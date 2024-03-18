<?php

namespace App\Models;

use App\Models\Product;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Relations\BelongsTo;

class ProductVariation extends Model
{
    use HasFactory;

    protected $fillable = [
        'product_id',
        'unit',
        'stock',
        'price',
        'price_grocery'
    ];

    protected $hidden = [
        'created_at',
        'updated_at'
    ];

    public function product() : BelongsTo
    {
        return $this->belongsTo(Product::class);
    }
}
