<?php

namespace App\Http\Controllers;

use App\Repository\ProductImageRepository;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;

class ProductImageController extends Controller
{
    protected ProductImageRepository $productImageRepository;

    public function __construct()
    {
        $this->productImageRepository = new ProductImageRepository();
    }

    public function updateImages(Request $request): JsonResponse
    {
        $request->validate([
            'product_id' => 'required|exists:products,id',
            'new_image' => 'required|image|mimes:jpeg,png,jpg,gif,svg|max:2048'
        ]);

        return response()->json($this->productImageRepository->updateImage($request));
    }

    public function addImage(Request $request): JsonResponse
    {
        $request->validate([
            'product_id' => 'required|exists:products,id',
            'image' => 'required|image|mimes:jpeg,png,jpg,gif,svg|max:2048'
        ]);

        return response()->json($this->productImageRepository->createNewImage($request->file('image'), $request->product_id));
    }
}
