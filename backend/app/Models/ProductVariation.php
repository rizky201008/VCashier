<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Casts\Attribute;
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
        'price_capital',
        'price_grocery'
    ];

    protected $hidden = [
        'created_at',
        'updated_at'
    ];

    public function product(): BelongsTo
    {
        return $this->belongsTo(Product::class);
    }

//    protected function price(): Attribute
//    {
//        return Attribute::make(
//            get: fn(string $value) => "Rp" . number_format($value, 0, ',', '.'),
//        );
//    }
//
//    protected function priceGrocery(): Attribute
//    {
//        return Attribute::make(
//            get: fn(string $value) => "Rp" . number_format($value, 0, ',', '.'),
//        );
//    }
}
