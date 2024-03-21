<?php

namespace App\Data;

use App\Models\Product;
use Illuminate\Http\File;
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

    public function createImage(Request $request): array
    {
        $product = $this->product->find($request->product_id);

        if ($product->image_path != null) {
            throw new \Exception('image aleady exsists');
        }

        $this->saveImage($request);

        return [
            'message' => 'Image uploaded successfully'
        ];
    }

    public function updateImage(Request $request)
    {
        $product = $this->product->find($request->product_id);
        $image = $request->file('new_image');
        $image_path = $product->image_path;
        $this->deleteImage($image_path);
        $this->saveImage($request);

        return ['message' => 'product image updated'];
    }

    private function replaceImage(array $data): void
    {
        $product = $this->product->find($data['product_id']);
        $product->update([
            'image_path' => $data['new_path'],
            'image_url' => $data['new_url']
        ]);
    }

    private function saveImage(Request $request): void
    {
        $file = $request->file('image');
        $file->store('product');
        $path = Storage::path('product/' . $file->hashName());
        $url = Storage::url('product/' . $file->hashName());
        $this->replaceImage([
            'product_id' => $request->product_id,
            'new_path' => $path,
            'new_url' => $url
        ]);
    }

    private function deleteImage($path): void
    {
        FileFacade::delete($path);
    }
}
