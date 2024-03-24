<?php

namespace App\Data;

use App\Models\Product;
use App\Models\Transaction;
use Illuminate\Database\Eloquent\Collection;
use Illuminate\Support\Facades\DB;

class TransactionRepository
{
    private Transaction $transaction;

    public function __construct()
    {
        $this->transaction = new Transaction();
    }

    public function getTransactions(): Collection
    {
        return $this->transaction->with('items')->get();
    }

    public function getTransaction(int $id): Transaction
    {
        return $this->transaction->with('items')->find($id);
    }

    public function createTransaction(array $data): array
    {
        DB::beginTransaction();
        try {
            $transaction = $this->transaction->create($data);
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

            DB::commit();

            return [
                'message' => 'Transaction created successfully',
            ];
        } catch (\Exception $e) {
            DB::rollBack();
            throw $e;
        }
    }
}
