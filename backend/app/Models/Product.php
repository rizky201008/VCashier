<?php

namespace App\Models;

use App\Models\ProductVariation;
use App\Models\ProductImage;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\HasMany;
use Illuminate\Database\Eloquent\Factories\HasFactory;

class Product extends Model
{
    use HasFactory;

    protected $fillable = [
        'name',
        'description',
        'slug',
        'category_id',
        'image_path'
    ];

    protected $hidden = [
        'created_at',
        'updated_at'
    ];

    public function variations() : HasMany
    {
        return $this->hasMany(ProductVariation::class);
    }

    public function images() : HasMany
    {
        return $this->hasMany(ProductImage::class);
    }

}
