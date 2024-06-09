<?php

namespace App\Data;

use App\Models\Transaction;
use App\Models\TransactionItem;
use Exception;
use Illuminate\Http\JsonResponse;
use Illuminate\Support\Facades\Validator;

class TransactionRepository
{
    public function getTotalAmount(array $items): int
    {
        $totalAmount = 0;
        $productRepository = new ProductRepository();
        foreach ($items as $item) {
            $validator = Validator::make($item, [
                'id' => 'required|exists:product_variations,id'
            ]);
            if ($validator->fails()) {
                throw new Exception($validator->messages()->first());
            }
            $productVariation = $productRepository->getProductVariation($item['id']);
            if ($this->stockExists($item['quantity'], $productVariation->stock)) {
                $totalAmount += $this->getPriceByQuantity($item['quantity'], $productVariation->price);
            } else {
                throw new Exception('Stock is not enough');
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

    public function saveTransactionItems(array $data, string $transactionId)
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

    public function getTransactionById(string $id)
    {
        return Transaction::with('items')->find($id);
    }

    public function getTransactions()
    {
        return ['data' => Transaction::with('items', 'items.productVariation', 'items.productVariation.product', 'customer')->orderBy('created_at', 'desc')->get()];
    }

    public function stockExists(int $quantity, int $stock): bool
    {
        return $quantity <= $stock;
    }
}
