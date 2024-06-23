<?php

namespace App\Repository;

use App\Models\Product;
use Illuminate\Http\Request;
use Illuminate\Http\UploadedFile;
use Illuminate\Support\Facades\Storage;
use Illuminate\Support\Facades\File as FileFacade;

class ProductImageRepository
{
    protected Product $product;

    public function __construct()
    {
        $this->product = new Product();
    }

    public function updateImage(Request $request)
    {
        $product = $this->product->find($request->product_id);
        $image_path = $product->image_path;
        $this->deleteImage($image_path);
        $save = $this->saveImage($request->file('new_image'), $request->product_id);

        $this->replaceImage([
            'product_id' => $save[2],
            'new_path' => $save[0],
            'new_url' => $save[1]
        ]);

        return ['message' => 'product image updated'];
    }

    public function createNewImage($file, $product_id)
    {
        $save = $this->saveImage($file, $product_id);
        $imageExists = $this->imageExists($product_id);

        if ($imageExists) {
            throw new \Exception('product image already exists, if you want to update use /update endpoint');
        }

        $this->replaceImage([
            'product_id' => $save[2],
            'new_path' => $save[0],
            'new_url' => $save[1]
        ]);

        return ['message' => 'product image created'];
    }

    private function imageExists($product_id): bool
    {
        $product = $this->product->find($product_id);
        return $product->image_path !== null;
    }

    private function replaceImage(array $data): void
    {
        $product = $this->product->find($data['product_id']);
        $product->update([
            'image_path' => $data['new_path'],
            'image_url' => $data['new_url']
        ]);
    }

    private function saveImage(array|UploadedFile $file, int $id): array|string
    {
        $file->store('product');
        $path = Storage::path('product/' . $file->hashName());
        $url = $file->hashName();
        return [$path, $url, $id];
    }

    private function deleteImage($path): void
    {
        if (FileFacade::exists($path)) {
            FileFacade::delete($path);
        }
    }
}
