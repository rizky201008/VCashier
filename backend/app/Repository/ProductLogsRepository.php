<?php

namespace App\Repository;

use App\Models\ProductLog;
use App\Models\ProductVariation;
use Illuminate\Support\Facades\DB;

class ProductLogsRepository
{
    public function getAllLogs(): \Illuminate\Database\Eloquent\Collection
    {
        return ProductLog::with(['productVariation', 'productVariation.product', 'user'])->latest()->get();
    }

    public function addLog(array $data): void
    {
        DB::beginTransaction();
        try {
            ProductLog::create($data);
            $productVariation = ProductVariation::find($data['product_variation_id']);
            if ($data['type'] === 'increase') {
                $productVariation->stock += $data['amount'];
            } else {
                if (!$this->validateStockAmount($data['amount'], $productVariation->stock)) {
                    throw new \Exception('Decrease amount must be lower than stock amount.');
                }
                $productVariation->stock -= $data['amount'];
            }
            $productVariation->save();
            DB::commit();
        } catch (\Throwable $e) {
            DB::rollBack();
            throw new \Exception($e->getMessage());
        }
    }

    private function validateStockAmount(int $amount, int $stock): bool
    {
        return $stock >= $amount;
    }
}
