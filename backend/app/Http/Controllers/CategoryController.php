<?php

namespace App\Http\Controllers;

use App\Models\Category;
use Illuminate\Http\Request;
use App\Logic\App as AppLogic;
use Illuminate\Support\Facades\Validator;

class CategoryController extends Controller
{
    protected $category;
    protected $appLogic;

    public function __construct(Category $category = new Category(), AppLogic $appLogic = new AppLogic())
    {
        $this->category = $category;
        $this->appLogic = $appLogic;
    }

    public function getCategories()
    {
        return $this->category->all();
    }

    public function getCategory($slug)
    {
        return $this->category->where('slug', $slug)->first();
    }

    public function createCategory(Request $request)
    {
        $this->validate($request, [
            'name' => 'required|string|max:255',
        ]);

        $category = $this->category->create([
            'name' => $request->name,
            'slug' => $this->appLogic->createSlug($request->name),
        ]);

        return response()->json(['message' => 'Category created successfully', 'category' => $category], 201);
    }

    public function updateCategory(Request $request)
    {
        $this->validate($request, [
            'name' => 'required|string|max:255',
            'id' => 'required|integer|exists:categories,id',
        ]);

        $id = $request->id;
        // Mengambil kategori yang akan diperbarui
        $category = $this->category->find($id);

        // Memperbarui kategori
        $category->update([
            'name' => $request->name,
            'slug' => $this->appLogic->createSlug($request->name),
        ]);

        // Mengembalikan kategori yang telah diperbarui sebagai respons
        return response()->json(['message' => 'Category updated successfully', 'category' => $category], 200);
    }

    public function deleteCategory($id)
    {
        $validated = Validator::make(['id' => $id], [
            'id' => 'required|integer|exists:categories,id',
        ]);

        if ($validated->fails()) {
            return response()->json(['message' => "Err : " . $validated->errors()->first()], 404);
        }

        $this->category->find($id)->delete();
        return response()->json(['message' => 'Category deleted successfully'], 200);
    }
}
