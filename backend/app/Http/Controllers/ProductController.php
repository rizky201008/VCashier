<?php

namespace App\Http\Controllers;

use App\Repository\ProductRepository;
use Exception;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class ProductController extends Controller
{

    protected ProductRepository $productRepository;

    public function __construct()
    {
        $this->productRepository = new ProductRepository();
    }

    public function createProduct(Request $request)
    {
        $request->validate([
            'name' => 'required',
            'description' => 'required',
            'variations' => 'required|array|min:1',
            'category_id' => 'exists:categories,id'
        ]);

        return $this->productRepository->createProduct($request);
    }

    public function updateProduct(Request $request)
    {
        $request->validate([
            'id' => 'required|exists:products,id',
            'category_id' => 'required|exists:categories,id',
        ]);

        $this->productRepository->updateProduct($request->all(), $request->id);
        return response()->json([
            'message' => 'Product updated successfully'
        ]);
    }

    public function updateProductVariation(Request $request)
    {
        $request->validate([
            'id' => 'required|exists:product_variations,id',
        ]);

        $this->productRepository->updateProductVariation($request->all());
        return response()->json([
            'message' => 'Product variation updated successfully'
        ]);
    }

    public function getProductVariation($id)
    {
        return response()->json($this->productRepository->getProductVariation($id));
    }

    public function getProduct($id)
    {
        $validation = Validator::make(['id' => $id], [
            'id' => 'required|exists:products,id'
        ]);

        if ($validation->fails()) {
            throw new Exception($validation->messages()->first());
        }
        return response()->json($this->productRepository->getProduct($id));
    }

    public function getProducts(): JsonResponse
    {
        return response()->json($this->productRepository->getProducts());
    }
}
