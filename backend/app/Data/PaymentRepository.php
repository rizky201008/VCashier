<?php

namespace App\Data;

use App\Models\PaymentMethod;
use App\Models\TransactionPayment;
use Illuminate\Database\Eloquent\Collection;

class PaymentRepository
{
    private PaymentMethod $paymentMethod;
    private TransactionPayment $transactionPayment;

    public function __construct()
    {
        $this->paymentMethod = new PaymentMethod();
        $this->transactionPayment = new TransactionPayment();
    }

    public function getAllPaymentMethod(): Collection
    {
        return $this->paymentMethod->all();
    }

    public function createTransactionPayment(array $data): void
    {
        $data = [
            'transaction_id' => $data['transaction_id'],
            'payment_method_id' => $data['payment_method_id'],
            'amount' => $data['amount'],
            'token' => $data['token'],
            'change' => $data['change'] ?? 0.0
        ];

        $this->transactionPayment->create($data);
    }
}
