<?php

namespace App\Http\Controllers;

use App\Data\ProductImageRepository;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;
use App\Logic\ProductImage as ProductImageLogic;

class ProductImageController extends Controller
{
    protected ProductImageRepository $productImageRepository;

    public function __construct()
    {
        $this->productImageRepository = new ProductImageRepository();
    }

    public function createImages(Request $request): JsonResponse
    {
        $request->validate([
            'product_id' => 'required|exists:products,id',
            'image' => 'required|mimes:jpeg,png,jpg|max:2048'
        ]);

        return response()->json($this->productImageRepository->createImage($request));

    }

    public function updateImages(Request $request): JsonResponse
    {
        $request->validate([
            'product_id' => 'required|exists:products,id',
            'new_image.*' => 'required|image|mimes:jpeg,png,jpg,gif,svg|max:2048'
        ]);

        return response()->json($this->productImageRepository->updateImage($request));
    }
}
