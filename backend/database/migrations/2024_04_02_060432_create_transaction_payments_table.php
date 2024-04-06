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
        Schema::create('transaction_payments', function (Blueprint $table) {
            $table->id();
            $table->foreignId('transaction_id');
            $table->foreignId('payment_method_id');
            $table->string('amount');
            $table->uuid('order_id');
            $table->enum('status', ['success', 'canceled', 'unpaid'])->default('unpaid');
            $table->string('change')->nullable();
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('transaction_payments');
    }
};
