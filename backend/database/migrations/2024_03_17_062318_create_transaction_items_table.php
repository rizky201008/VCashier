<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration {
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('transaction_items', function (Blueprint $table) {
            $table->id();
            $table->foreignUuid('transaction_id')->constrained()->onDelete('cascade')->onUpdate('cascade');
            $table->foreignIdFor(\App\Models\ProductVariation::class)->constrained()->onDelete('cascade')->onUpdate('cascade');
            $table->bigInteger('quantity');
            $table->bigInteger('price');
            $table->bigInteger('profit');
            $table->bigInteger('subtotal');
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('transaction_items');
    }
};
