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
            $table->foreignIdFor(\App\Models\Transaction::class)->constrained()->onDelete('restrict');
            $table->foreignIdFor(\App\Models\PaymentMethod::class)->constrained()->onDelete('restrict');
            $table->double('amount');
            $table->double('change')->default(0.0);
            $table->string('token')->nullable();
            $table->enum('status',['completed','unpaid'])->default('pending');
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
