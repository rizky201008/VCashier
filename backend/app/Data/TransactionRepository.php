<?php

namespace App\Data;

use App\Models\Transaction;
use App\Models\TransactionItem;

class TransactionRepository
{
    public function getTotalAmount(array $items): int
    {
        $totalAmount = 0;
        $productRepository = new ProductRepository();
        foreach ($items as $item) {
            $productVariation = $productRepository->getProductVariation($item['id']);
            if ($this->stockExists($item['quantity'], $productVariation->stock)) {
                $totalAmount += $this->getPriceByQuantity($item['quantity'], $productVariation->price);
            } else {
                throw new \Exception('Stock is not enough');
            }
        }

        return $totalAmount;
    }

    public function getPriceByQuantity(int $quantity, int $price)
    {
        return $quantity * $price;
    }

    public function saveTransaction(array $data)
    {
        return Transaction::create($data);
    }

    public function saveTransactionItems(array $data, int $transactionId)
    {
        $productRepository = new ProductRepository();
        $transactionItems = [];
        foreach ($data['items'] as $item) {
            $productVariation = $productRepository->getProductVariation($item['id']);
            $transactionItems[] = [
                'transaction_id' => $transactionId,
                'product_variation_id' => $item['id'],
                'quantity' => $item['quantity'],
                'price' => $productVariation->price,
                'subtotal' => $this->getPriceByQuantity($item['quantity'], $productVariation->price),
            ];
            $productRepository->decreaseStock($item['id'], $item['quantity']);
        }

        return TransactionItem::insert($transactionItems);
    }

    public function getTransactionById(int $id): \Illuminate\Database\Eloquent\Model|\Illuminate\Database\Eloquent\Collection|\Illuminate\Database\Eloquent\Builder|array|null
    {
        return Transaction::with('items', 'payment')->find($id);
    }

    public function getTransactions(): \Illuminate\Database\Eloquent\Collection
    {
        return Transaction::with('items', 'payment')->orderBy('created_at', 'desc')->get();
    }

    public function stockExists(int $quantity, int $stock): bool
    {
        return $quantity <= $stock;
    }
}
