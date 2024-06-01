<?php

namespace App\Data;

use App\Models\PaymentMethod;
use Illuminate\Database\Eloquent\Collection;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Midtrans\Config as MidtransConfig;
use Midtrans\Snap as MidtransSnap;

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

    public function createVa($transaction): string
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
        ];

        foreach ($transaction->items() as $data) {
            $data['item_details'][] = [
                'id' => $data->id,
                'price' => $data->price,
                'quantity' => $data->quantity,
                'name' => $data->product->name . ' ' . $data->productVariation->name
            ];
        }
        try {
            // Set your Merchant Server Key
            MidtransConfig::$serverKey = env("MIDTRANS_SERVER_KEY");
            // Set to Development/Sandbox Environment (default). Set to true for Production Environment (accept real transaction).
            MidtransConfig::$isProduction = false;
            // Set sanitization on (default)
            MidtransConfig::$isSanitized = true;
            // Set 3DS transaction for credit card to true
            MidtransConfig::$is3ds = true;
            MidtransConfig::$overrideNotifUrl = "https://example.com";
            $token = MidtransSnap::createTransaction($data);

            return $token->token;
        } catch (\Exception $th) {
            throw new \Exception("MIDTRANS Err :".$th->getMessage());
        }
    }

    private function validateAmount(int $userAmount, int $totalAmount)
    {
        return $userAmount >= $totalAmount;
    }

    private function amountMatch(int $userAmount, int $totalAmount)
    {
        return $userAmount == $totalAmount;
    }

    public function createTransactionPayment(Request $request)
    {
        try {
            DB::beginTransaction();
            $data = $request->all();
            $updateTransaction = $this->insertTransactionPayment($data);
            $repo = new TransactionRepository();
            $transaction = $repo->getTransactionById($data['transaction_id']);
            $va = $this->createVa($transaction);
            $transaction->update([
                'va_number' => $va
            ]);
            DB::commit();
            return response()->json([
                'message' => 'Make payment success'
            ]);
        } catch (\Exception $th) {
            DB::rollBack();
            return response()->json([
                'message' => $th->getMessage()
            ], 500);
        }
    }

    public function insertTransactionPayment(array $data)
    {
        $transactionRepository = new TransactionRepository();
        $paymentMethod = $this->getPaymentMethod($data['payment_method_id']);
        $transaction = $transactionRepository->getTransactionById($data['transaction_id']);
        $transactionTotal = $transaction->total_amount;
        if ($paymentMethod->cash) {
            if ($this->validateAmount($data['payment_amount'], $transactionTotal)) {
                $data['change'] = $data['payment_amount'] - $transactionTotal;
                $transaction->update([
                    'payment_status' => 'paid'
                ]);
            } else {
                throw new \Exception('Amount is not enough');
            }
        } else {
            if ($this->amountMatch($data['payment_amount'], $transactionTotal)) {
                $data['payment_amount'] += $paymentMethod->fee;
            } else {
                throw new \Exception('Amount and transaction amount must be same');
            }
        }
        return $transaction->update($data);
    }
}
