<?php

namespace App\Repository;

use App\Models\Transaction;
use App\Models\TransactionItem;
use Exception;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Validator;

class TransactionRepository
{
    public function processTransaction(array $data): array
    {
        DB::beginTransaction();

        try {
            $transaction = $this->saveTransaction($data);
            $this->saveTransactionItems($data, $transaction->id);
            $this->createLog($data);
            DB::commit();

            return [
                'message' => 'Transaction created',
                'transaction_id' => $transaction->id
            ];
        } catch (Exception $e) {
            DB::rollBack();
            throw new Exception($e->getMessage());
        }
    }

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
                if ($item['grocery']) {
                    $price = $productVariation->price_grocery;
                } else {
                    $price = $productVariation->price;
                }
                $totalAmount += $this->getPriceByQuantity($item['quantity'], $price);
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
            if ($item['grocery']) {
                $price = $productVariation->price_grocery;
            } else {
                $price = $productVariation->price;
            }
            $capital = $productVariation->price_capital;
            $transactionItems[] = [
                'transaction_id' => $transactionId,
                'product_variation_id' => $item['id'],
                'quantity' => $item['quantity'],
                'price' => $price,
                'subtotal' => $this->getPriceByQuantity($item['quantity'], $price),
                'profit' => $this->getProfit($capital, $item['quantity'], $price),
                'created_at' => now(),
            ];
        }

        return TransactionItem::insert($transactionItems);
    }

    private function getProfit(int $capital, int $amount, int $price): int
    {
        $profit = $price - $capital;
        return $profit * $amount;
    }

    public function getTransactionById(string $id)
    {
        return Transaction::with(['items', 'payment_method'])->find($id);
    }

    public function getTransactions($status, $paymentStatus)
    {
        $trx = Transaction::with('items', 'items.productVariation', 'items.productVariation.product', 'customer')->orderBy('created_at', 'desc');
        if ($status && $status !== "semua") {
            $trx->where('transaction_status', $status);
        }
        if ($paymentStatus) {
            $trx->where('payment_status', $paymentStatus);
        }
        return ['data' => $trx->get()];
    }

    public function stockExists(int $quantity, int $stock): bool
    {
        return $quantity <= $stock;
    }

    public function updateTransaction(array $data): array
    {
        $productLogRepository = new ProductLogsRepository();
        $transaction = Transaction::find($data['id']);
        if ($data['transaction_status'] == "canceled") {
            $data['payment_status'] = "canceled";
            foreach ($transaction->items as $item) {
                $productLogRepository->addLog([
                    'product_variation_id' => $item->product_variation_id,
                    'amount' => $item->quantity,
                    'type' => 'increase',
                    'information' => 'Transaction canceled',
                    'user_id' => $data['user_id']
                ]);
            }
        }

        $transaction->update($data);
        return [
            'message' => 'Transaction updated'
        ];
    }

    private function createLog(array $data)
    {
        $productLog = new ProductLogsRepository();

        foreach ($data['items'] as $item) {
            $productLog->addLog([
                'product_variation_id' => $item['id'],
                'amount' => $item['quantity'],
                'type' => 'decrease',
                'information' => 'Transaction',
                'user_id' => $data['user_id']
            ]);
        }
    }
}
