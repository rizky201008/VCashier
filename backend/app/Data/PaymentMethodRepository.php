<?php

namespace App\Data;

use App\Models\PaymentMethod;
use Illuminate\Database\Eloquent\Collection;

class PaymentMethodRepository
{
    private $paymentMethod;

    public function __construct()
    {
        $this->paymentMethod = new PaymentMethod();
    }

    public function getAllPaymentMethod(): Collection
    {
        return $this->paymentMethod->all();
    }
}
