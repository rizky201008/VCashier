<?php

namespace App\Repository;

use App\Models\Product;
use App\Models\ProductVariation;
use Illuminate\Database\Eloquent\Collection;
use Illuminate\Http\JsonResponse;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Str;
use PHPUnit\Framework\MockObject\Exception;

class ProductRepository
{

    private Product $product;
    private ProductVariation $variation;

    function __construct()
    {
        $this->product = new Product();
        $this->variation = new ProductVariation();
    }

    function getProducts(): array
    {
        return ['data' => Product::with(['variations', 'category'])->get()];
    }

    function getProduct($id): object
    {
        return $this->product->with(['variations', 'category'])->where('id', $id)->first();
    }

    public function getProductVariation($id): ProductVariation
    {
        return ProductVariation::where('id', $id)->first();
    }

    public function decreaseStock(int $productVariationId, int $quantity)
    {
        $productVariation = $this->getProductVariation($productVariationId);
        $productVariation->stock -= $quantity;
        $productVariation->save();
    }
    public function increaseStock(int $productVariationId, int $quantity)
    {
        $productVariation = $this->getProductVariation($productVariationId);
        $productVariation->stock += $quantity;
        $productVariation->save();
    }

    function createProduct($data): JsonResponse
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
                ProductVariation::create([
                    'unit' => $variation['unit'],
                    'stock' => $variation['stock'],
                    'price' => $variation['price'],
                    'price_capital' => $variation['price_capital'] ?? 0,
                    'price_grocery' => $variation['price_grocery'],
                    'product_id' => $createdProduct->id
                ]);
            }

            DB::commit();

            return response()->json(['message' => 'Product created successfully', 'id' => $createdProduct->id], 201);
        } catch (\Throwable $throwable) {
            DB::rollBack();

            throw new \Exception($throwable->getMessage());
        }
    }

    function updateProduct(array $data, $productId): void
    {
        $product = Product::find($productId);

        DB::beginTransaction();

        try {

            $product->update($data);

            DB::commit();

        } catch (\Throwable $th) {
            DB::rollBack();

            throw new \Exception($th->getMessage());
        }
    }

    public function updateProductVariation(array $data): void
    {
        DB::beginTransaction();
        try {
            $productVariation = ProductVariation::find($data['id']);
            $productVariation->update($data);
            DB::commit();
        } catch (\Throwable $th) {
            DB::rollBack();

            throw new \Exception($th->getMessage());
        }
    }

}
