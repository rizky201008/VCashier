<?php

namespace App\Http\Controllers;

use App\Data\CategoryRepository;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class CategoryController extends Controller
{
    protected CategoryRepository $categoryRepository;

    public function __construct()
    {
        $this->categoryRepository = new CategoryRepository();
    }

    public function getCategories()
    {
        return response()->json(['data' => $this->categoryRepository->getAllCategories()]);
    }

    public function getCategory($id)
    {
        return response()->json($this->categoryRepository->getCategory($id));
    }

    public function createCategory(Request $request)
    {
        $this->validate($request, [
            'name' => 'required|string|max:255',
        ]);

        $category = $this->categoryRepository->createCategory($request->name);
        return response()->json(['message' => 'Category created successfully', 'category' => $category], 201);
    }

    public function updateCategory(Request $request)
    {
        $this->validate($request, [
            'name' => 'required|string|max:255',
            'id' => 'required|integer|exists:categories,id',
        ]);

        $this->categoryRepository->updateCategory($request->id, $request->all());

        // Mengembalikan kategori yang telah diperbarui sebagai respons
        return response()->json(['message' => 'Category updated successfully'], 200);
    }

    public function deleteCategory($id)
    {
        $validated = Validator::make(['id' => $id], [
            'id' => 'required|integer|exists:categories,id',
        ]);

        if ($validated->fails()) {
            return response()->json(['message' => "Err : " . $validated->errors()->first()], 404);
        }

        $this->categoryRepository->deleteCategory($id);
        return response()->json(['message' => 'Category deleted successfully'], 200);
    }
}
