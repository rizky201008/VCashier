<?php

namespace App\Http\Controllers;

use App\Repository\CategoryRepository;
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

        $this->categoryRepository->createCategory($request->name);

        return response()->json(['message' => 'Category created successfully'], 201);
    }

    public function updateCategory(Request $request)
    {
        $this->validate($request, [
            'name' => 'required|string|max:255',
            'id' => 'required|integer|exists:categories,id',
        ]);

        $this->categoryRepository->updateCategory($request->id, $request->all());

        return response()->json(['message' => 'Category updated successfully'], 200);
    }
}
