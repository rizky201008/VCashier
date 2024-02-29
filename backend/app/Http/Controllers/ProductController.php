<?php

namespace App\Http\Controllers;

use App\Models\Product;
use Illuminate\Http\Request;
use App\Logic\App as AppLogic;
use App\Models\ProductVariation;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Validator;

class ProductController extends Controller
{
    protected $appLogic;
    protected $product;
    protected $variation;

    public function __construct()
    {
        $this->appLogic = new AppLogic();
        $this->product = new Product();
        $this->variation = new ProductVariation();
    }
    public function createProduct(Request $request)
    {
        $request->validate([
            'name' => 'required',
            'description' => 'required',
            'category_id' => 'required|exists:categories,id',
            'variations' => 'required|array|min:1',
        ]);

        DB::beginTransaction();

        try {
            $product = Product::create([
                'name' => $request->name,
                'description' => $request->description,
                'slug' => $this->appLogic->createSlug($request->name),
                'category_id' => $request->category_id
            ]);

            $productVariations = $request->variations;

            foreach ($productVariations as $variation) {
                $this->variation->create([
                    'name' => $variation['name'],
                    'stock' => $variation['stock'],
                    'price' => $variation['price'],
                    'price_grocery' => $variation['price_grocery'],
                    'product_id' => $product->id
                ]);
            }

            DB::commit();
            return response()->json([
                'message' => 'Product created successfully'
            ], 201);
        } catch (\Throwable $th) {
            DB::rollBack();

            throw $th;
        }
    }

    public function updateProduct(Request $request, $id)
    {
        $validator = Validator::make(array_merge(['id' => $id], $request->all()), [
            'id' => 'required|exists:products,id',
            'category_id' => 'required|exists:categories,id',
        ]);

        if ($validator->fails()) {
            throw $validator->messages()->first();
        }

        $product = Product::find($id);

        if ($request->name !== $product->name) {
            $product->slug = $this->appLogic->createSlug($request->name);
        }

        DB::beginTransaction();

        try {

            $product->update($request->all());

            $variations = $request->variations;

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
            return response()->json([
                'message' => 'Product updated successfully'
            ], 200);
        } catch (\Throwable $th) {
            DB::rollBack();

            throw $th;
        }
    }

    public function deleteProduct($id)
    {
        $validator = Validator::make(['id' => $id], [
            'id' => 'required|exists:products,id'
        ]);

        if ($validator->fails()) {
            throw $validator->messages()->first();
        }

        DB::beginTransaction();

        try {
            $product = Product::where('id', $id)->first();
            $product->variations()->delete();
            $product->images()->delete();
            $product->delete();

            DB::commit();
            return response()->json([
                'message' => 'Product deleted successfully'
            ], 200);
        } catch (\Throwable $th) {
            DB::rollBack();

            throw $th;
        }
    }

    public function getProduct($slug)
    {
        $validator = Validator::make(['slug' => $slug], [
            'slug' => 'required|exists:products,slug'
        ]);

        if ($validator->fails()) {
            return response()->json($validator->messages()->first());
        }

        $product = Product::with(['images', 'variations'])->where('slug', $slug)->first();

        return response()->json($product, 200);
    }

    public function getProducts()
    {
        $products = Product::with(['images', 'variations'])->get();

        return response()->json($products, 200);
    }
}
