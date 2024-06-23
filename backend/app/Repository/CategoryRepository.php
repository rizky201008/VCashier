<?php

namespace App\Repository;
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

    public function createCategory($name) : void
    {
        $this->category->create([
            'name' => $name,
        ]);
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
