<?php

namespace App\Data;
use App\Models\Category;

class CategoryRepository
{
    protected $category;

    public function __construct()
    {
        $this->category = new Category();
    }

    public function getAllCategories()
    {
        return $this->category->all();
    }

    public function getCategory($id)
    {
        return $this->category->find($id);
    }

    public function createCategory($name)
    {
        $category = $this->category->create([
            'name' => $name,
        ]);

        return $category;
    }

    public function updateCategory($id, $reqs): void
    {
        $this->category->find($id)->update($reqs);
    }

    public function deleteCategory($id): void
    {
        $this->category->find($id)->delete();
    }
}
