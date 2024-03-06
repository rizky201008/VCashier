<?php

namespace App\Logic;

use App\Models\Product;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\File;
use App\Models\ProductImage as ProductImageModel;

class ProductImage
{
    protected $product;
    protected $productImage;
    protected $file;

    public function __construct()
    {
        $this->product = new Product();
        $this->productImage = new ProductImageModel();
        $this->file = new File();
    }

    public function countImages($productId)
    {
        $product = $this->product->find($productId);

        $imageCount = count($product->images);
        return $imageCount ?? 0;
    }

    public function addProductImage($productId, $images)
    {
        $imagesCount = $this->countImages($productId);
        DB::beginTransaction();
        try {
            $files = [];
            if ($imagesCount <= 4 && $imagesCount + count($images) <= 4) {
                foreach ($images as $file) {
                    if ($file->isValid()) {
                        $filename = round(microtime(true) * 1000) . '-' . str_replace(' ', '-', $file->getClientOriginalName());
                        $file->move(public_path('products'), $filename);
                        $files[] = [
                            'product_id' => $productId,
                            'path' =>  'products' . '/' . $filename,
                        ];
                    }
                }
                $this->productImage->insert($files);
                DB::commit();
                return true;
            }
            throw new \Exception('Image count exceeds the limit, maximum 4 images allowed. you can only add ' . (4 - $imagesCount) . ' images.');
        } catch (\Throwable $th) {
            DB::rollBack();
            throw $th;
            return false;
        }
    }

    public function deleteProductImage($imagePath)
    {
        $productImage = $this->productImage->where('path', $imagePath);
        File::delete($imagePath);
        $productImage->delete();

        return true;
    }

    public function updateProductImage($productId, $imagePath, $newImage)
    {
        $productImage = $this->productImage->where('path', $imagePath);
        $productImage->delete();
        File::delete($imagePath);
        $this->addProductImage($productId, $newImage);

        return true;
    }

    public function deleteProductImages($productId)
    {
        $productImages = $this->productImage->where('product_id', $productId)->get();
        foreach ($productImages as $productImage) {
            File::delete($productImage->path);
            $productImage->delete();
        }

        return true;
    }
}
