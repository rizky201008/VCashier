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
        $transaction = Transaction::with(['paymentMethod'])->where('id', $data['transaction_id'])->first();
        try {
            DB::beginTransaction();

            $transactionTotal = $transaction->total_amount;

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

            if (!$paymentMethod->cash) {
                $this->createVa($transaction);
            }

            DB::commit();

            return [
                'message' => 'Make payment success',
                'id' => $data['transaction_id']
            ];

        } catch (\Exception $th) {
            DB::rollBack();
            return [
                'message' => "Err: db catch " . $th->getMessage()
            ];
        }
    }

    public function createVa($transaction): void
    {
        $data = [
            "transaction_details" => [
                "gross_amount" => $transaction->payment_amount,
                "order_id" => $transaction->id
            ],
            "customer_details" => [
                "first_name" => $transaction->customer->name,
                "last_name" => $transaction->customer->name,
                "email" => "customer@gmail.com",
                "phone" => $transaction->customer->phone_number
            ],
            "payment_type" => "bank_transfer",
            "bank_transfer" => [
                "bank" => $transaction->paymentMethod->code
            ]
        ];

        foreach ($transaction->items() as $item) {
            $data['item_details'] = [
                'id' => "$item->id",
                'price' => $item->price,
                'quantity' => $item->quantity,
                'name' => $item->product->name . ' ' . $item->productVariation->name
            ];
        }

        try {
            MidtransConfig::$serverKey = env("MIDTRANS_SERVER_KEY");
            MidtransConfig::$isProduction = false;
            MidtransConfig::$isSanitized = true;
            MidtransConfig::$is3ds = true;
            MidtransConfig::$overrideNotifUrl = env("BASE_URL");
            MidtransConfig::$curlOptions[CURLOPT_SSL_VERIFYHOST] = 0;
            MidtransConfig::$curlOptions[CURLOPT_SSL_VERIFYPEER] = 0;
            MidtransConfig::$curlOptions[CURLOPT_HTTPHEADER] = [];

            $coreResponse = MidtransCoreApi::charge($data);
            $data[] = [];
            if ($transaction->paymentMethod->code == "permata") {
                $data['va_number'] = $coreResponse->permata_va_number;
            } else {
                $data['va_number'] = $coreResponse->va_numbers[0]->va_number;
            }
            $transaction->update($data);
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
