<?php

namespace App\Data;

use App\Models\PaymentMethod;
use App\Models\Product;
use App\Models\Transaction;
use App\Models\TransactionPayment;
use Illuminate\Database\Eloquent\Collection;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Str;

class TransactionRepository
{
    private Transaction $transaction;
    private PaymentRepository $payment;
    private PaymentMethod $paymentMethod;

    public function __construct()
    {
        $this->transaction = new Transaction();
        $this->payment = new PaymentRepository();
        $this->paymentMethod = new PaymentMethod();
    }

    public function getTransactions(): Collection
    {
        return $this->transaction->with(['items','payment'])->get();
    }

    public function getTransaction(int $id): Transaction
    {
        return $this->transaction->with(['items','payment'])->find($id);
    }

    public function createTransaction(array $data): array
    {
        DB::beginTransaction();
        try {
            $payment = $this->paymentMethod->find($data['payment_method_id']);
            $transactionData = array_merge($data, [
                'status' => ($payment->type == 'cash') ? 'completed' : 'unpaid',
            ]);
            $transaction = $this->transaction->create($transactionData);
            $items = [];

            foreach ($data['items'] as $item) {
                $product = Product::find($item['product_id']);
                $variation = $product->variations->find($item['variation_id']);
                if ($variation->stock < $item['qty']) {
                    DB::rollBack();
                    throw new \Exception('Stock is not enough');
                } else {
                    $items[] = [
                        'product' => $product->name,
                        'price' => $variation->price,
                        'qty' => $item['qty']
                    ];
                    $variation->stock -= $item['qty'];
                    $variation->save();
                }
            }

            $transaction->items()->createMany($items);
            $totalPrice = $this->getTotalPrice($items);
            $change = $data['payment_amount'] - $totalPrice;

            $validate = Validator::make($data, [
                'payment_method_id' => 'required|integer|exists:' . PaymentMethod::class . ',id',
                'payment_amount' => "required|gt:$totalPrice"
            ]);

            if ($validate->fails()) {
                DB::rollBack();
                throw new \Exception($validate->errors()->first());
            }

            $this->payment->createTransactionPayment([
                'transaction_id' => $transaction->id,
                'payment_method_id' => $data['payment_method_id'],
                'amount' => $data['payment_amount'],
                'token' => null,
                'change' => $change,
            ]);

            DB::commit();

            return [
                'message' => 'Transaction created successfully',
            ];
        } catch (\Exception $e) {
            DB::rollBack();
            throw $e;
        }
    }

    public function getTotalPrice(array $items): float
    {
        $total = 0.0;
        foreach ($items as $item) {
            $total += $item['price'] * $item['qty'];
        }
        return $total;
    }
}
