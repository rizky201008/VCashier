<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Logic\ProductImage as ProductImageLogic;

class ProductImageController extends Controller
{
    protected $productImageLogic;
    public function __construct()
    {
        $this->productImageLogic = new ProductImageLogic();
    }
    public function createImages(Request $request)
    {
        $request->validate([
            'product_id' => 'required|exists:products,id',
            'images.*' => 'required|mimes:jpeg,png,jpg|max:2048'
        ]);

        $created = $this->productImageLogic->addProductImage(
            $request->product_id,
            $request->file('images')
        );

        if ($created) {
            return response()->json([
                'message' => 'Image uploaded successfully'
            ], 201);
        }

        return response()->json([
            'message' => 'Image upload failed'
        ], 400);

    }
    public function updateImages(Request $request)
    {
        $request->validate([
            'product_id' => 'required|exists:products,id',
            'image_path' => 'required|exists:product_images,path',
            'new_image' => 'required|image|mimes:jpeg,png,jpg,gif,svg|max:2048'
        ]);

        $updated = $this->productImageLogic->updateProductImage(
            $request->product_id,
            $request->image_path,
            $request->file('new_image')
        );

        if ($updated) {
            return response()->json([
                'message' => 'Image updated successfully'
            ], 200);
        }

        return response()->json([
            'message' => 'Image update failed'
        ], 400);
    }
    public function deleteImage(Request $request)
    {
        $request->validate([
            'image_path' => 'required|exists:product_images,path'
        ]);

        $deleted = $this->productImageLogic->deleteProductImage($request->image_path);

        if ($deleted) {
            return response()->json([
                'message' => 'Image deleted successfully'
            ], 200);
        }

        return response()->json([
            'message' => 'Image delete failed'
        ], 400);
    }
    public function deleteImages(Request $request)
    {
        $request->validate([
            'product_id' => 'required|exists:products,id'
        ]);

        $deleted = $this->productImageLogic->deleteProductImages($request->product_id);

        if ($deleted) {
            return response()->json([
                'message' => 'Images deleted successfully'
            ], 200);
        }

        return response()->json([
            'message' => 'Images delete failed'
        ], 400);
    }
}
