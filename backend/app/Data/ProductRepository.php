<?php

namespace App\Data;

use App\Models\Product;
use App\Models\ProductVariation;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Str;
use PHPUnit\Framework\MockObject\Exception;

class ProductRepository
{

    private $product;
    private $variation;

    function __construct()
    {
        $this->product = new Product();
        $this->variation = new ProductVariation();
    }

    function getProducts()
    {
        return $this->product->with(['variations'])->get();
    }

    function getProduct($id)
    {
        return $this->product->with(['images', 'variations'])->where('id', $id)->first();
    }

    function createProduct($data)
    {
        DB::beginTransaction();
        try {
            $createdProduct = $this->product->create(
                [
                    'name' => $data->name,
                    'description' => $data->description,
                    'category_id' => $data->category_id ?? 1
                ]
            );

            $productVariations = $data->variations;

            foreach ($productVariations as $variation) {
                $this->variation->create([
                    'unit' => $variation['unit'],
                    'stock' => $variation['stock'],
                    'price' => $variation['price'],
                    'price_grocery' => $variation['price_grocery'],
                    'product_id' => $createdProduct->id
                ]);
            }

            DB::commit();

            return ['message' => 'Product created successfully', 'id' => $createdProduct->id];
        } catch (\Throwable $throwable) {
            DB::rollBack();

            throw new \Exception($throwable->getMessage());
        }
    }

    function updateProduct($data, $productId)
    {
        $product = Product::find($productId);

        DB::beginTransaction();

        try {

            $product->update($data->all());

            $variations = $data->variations;

            if ($variations !== null) {
                $product->variations()->delete();
                foreach ($variations as $variation) {
                    $this->variation->create(
                        [
                            'name' => $variation['name'],
                            'stock' => $variation['stock'],
                            'price' => $variation['price'],
                            'price_grocery' => $variation['price_grocery'],
                            'product_id' => $product->id
                        ]
                    );
                }
            }

            DB::commit();
            return [
                'message' => 'Product updated successfully'
            ];
        } catch (\Throwable $th) {
            DB::rollBack();

            throw new \Exception($th->getMessage());
        }
    }

    function deleteProduct($id)
    {
        DB::beginTransaction();

        try {
            $product = $this->product->where('id', $id)->first();
            $product->variations()->delete();
            $product->images()->delete();
            $product->delete();

            DB::commit();
            return [
                'message' => 'Product deleted successfully'
            ];
        } catch (\Throwable $th) {
            DB::rollBack();

            throw new \Exception($th->getMessage());
        }
    }

}
