<?php

use App\Models\Customer;
use App\Models\PaymentMethod;
use App\Models\User;
use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration {
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('transactions', function (Blueprint $table) {
            $table->uuid('id')->primary();
            $table->bigInteger('total_amount');
            $table->enum('transaction_status', ['pending', 'shipping', 'canceled', 'completed', 'draft'])->default('pending');
            $table->enum('payment_status', ['unpaid', 'paid', 'canceled', 'refunded'])->default('unpaid');
            $table->text('va_number')->nullable();
            $table->bigInteger('change')->nullable();
            $table->bigInteger('payment_amount')->nullable();
            $table->foreignIdFor(User::class)->constrained()->onDelete('cascade')->onUpdate('cascade');
            $table->foreignIdFor(Customer::class)->constrained()->onDelete('cascade')->onUpdate('cascade');
            $table->foreignIdFor(PaymentMethod::class)->nullable()->constrained()->onDelete('cascade')->onUpdate('cascade');
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('transactions');
    }
};
