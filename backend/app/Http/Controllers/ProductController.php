<?php

namespace App\Http\Controllers;

use App\Data\ProductRepository;
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

    public function updateProduct(Request $request, $id)
    {
        $validator = Validator::make(array_merge(['id' => $id], $request->all()), [
            'id' => 'required|exists:products,id',
            'category_id' => 'required|exists:categories,id',
        ]);

        if ($validator->fails()) {
            throw new Exception($validator->messages()->first());
        }

        return response()->json($this->productRepository->updateProduct($request, $id));
    }

    public function deleteProduct($id)
    {
        $validator = Validator::make(['id' => $id], [
            'id' => 'required|exists:products,id'
        ]);

        if ($validator->fails()) {
            throw new Exception($validator->messages()->first());
        }

        return response()->json($this->productRepository->deleteProduct($id));
    }

    public function getProduct($id)
    {
        return response()->json($this->productRepository->getProduct($id));
    }

    public function getProducts() : JsonResponse
    {
        return $this->productRepository->getProducts();
    }
}
