<?php

namespace App\Repository;

use App\Models\PaymentMethod;
use App\Models\Transaction;
use Illuminate\Database\Eloquent\Collection;
use Illuminate\Support\Facades\DB;
use Midtrans\Config as MidtransConfig;
use \Midtrans\CoreApi as MidtransCoreApi;

class PaymentRepository
{

    public function getAllPaymentMethod(): Collection
    {
        return PaymentMethod::all();
    }

    public function getPaymentMethod(string $id)
    {
        return PaymentMethod::find($id);
    }

    public function processPayment(array $data): array
    {
        $paymentMethod = $this->getPaymentMethod($data['payment_method_id']);
        $transaction = Transaction::with(['payment_method'])->where('id', $data['transaction_id'])->first();
        $transactionTotal = $transaction->total_amount;

        try {
            DB::beginTransaction();
            if ($paymentMethod->cash) {
                if ($this->validateAmount($data['payment_amount'], $transactionTotal)) {
                    $data['change'] = $data['payment_amount'] - $transactionTotal;
                    $data['payment_status'] = 'paid';
                    $data['transaction_status'] = 'completed';
                } else {
                    throw new \Exception('Amount is not enough');
                }
            } else {
                if ($this->amountMatch($data['payment_amount'], $transactionTotal)) {
                    $data['payment_amount'] += $paymentMethod->fee;
                    $data['transaction_status'] = 'pending';
                } else {
                    throw new \Exception('Amount and transaction amount must be the same: ' . $data['payment_amount'] . ' vs ' . $transactionTotal);
                }
            }

            $transaction->update($data);

            DB::commit();

            return [
                'message' => 'Make payment success',
                'id' => $transaction->id
            ];
        } catch (\Exception $th) {
            DB::rollBack();
            throw new \Exception("DB Error: " . $th->getMessage());
        }

    }

    public function processCreateVa($transactionId): string
    {
        $transaction = Transaction::with(['payment_method', 'items', 'items.productVariation.product', 'items.productVariation', 'customer', 'user'])->where('id', $transactionId)->first();
        if ($transaction->payment_method_id == null) {
            throw new \Exception("Payment method not set");
        }
        if ($transaction->va_number == null) {
            return $this->createVa($transaction);
        } else {
            throw new \Exception("VA already created");
        }
    }

    public function createVa($transaction): string
    {
        $data = [
            "transaction_details" => [
                "gross_amount" => $transaction->payment_amount,
                "order_id" => $transaction->id
            ],
            "customer_details" => [
                "first_name" => $transaction->customer->name,
                "last_name" => "",
                "email" => $transaction->user->email,
                "phone" => $transaction->customer->phone_number
            ],
            "payment_type" => "bank_transfer",
            "bank_transfer" => [
                "bank" => $transaction->payment_method->code
            ],
            "item_details" => []
        ];

        foreach ($transaction->items as $item) {
            $data['item_details'][] = [
                'id' => "$item->id",
                'price' => $item->price,
                'quantity' => $item->quantity,
                'name' => $item->productVariation->product->name . ' ' . $item->productVariation->unit
            ];
        }

        $data['item_details'][] = [
            'id' => '999',
            'price' => $transaction->payment_method->fee,
            'quantity' => 1,
            'name' => 'Biaya Admin'
        ];

        try {
            MidtransConfig::$serverKey = env("MIDTRANS_SERVER_KEY");
            MidtransConfig::$isProduction = env("MIDTRANS_PRODUCTION");
            MidtransConfig::$isSanitized = true;
            MidtransConfig::$is3ds = true;
            MidtransConfig::$overrideNotifUrl = env("BASE_URL").'/api/midtrans';
            MidtransConfig::$curlOptions[CURLOPT_SSL_VERIFYHOST] = 0;
            MidtransConfig::$curlOptions[CURLOPT_SSL_VERIFYPEER] = 0;
            MidtransConfig::$curlOptions[CURLOPT_HTTPHEADER] = [];

            $coreResponse = MidtransCoreApi::charge($data);
            $transactionData[] = [];
            if ($transaction->payment_method->code == "permata") {
                $transactionData['va_number'] = $coreResponse->permata_va_number;
            } else {
                $transactionData['va_number'] = $coreResponse->va_numbers[0]->va_number;
            }
            $transaction->update($transactionData);
            return $transactionData['va_number'];
        } catch (\Exception $th) {
            throw new \Exception("MIDTRANS Error: " . $th->getMessage());
        }
    }

    private function validateAmount(int $userAmount, int $totalAmount): bool
    {
        return $userAmount >= $totalAmount;
    }

    private function amountMatch(int $userAmount, int $totalAmount): bool
    {
        return $userAmount == $totalAmount;
    }

}
