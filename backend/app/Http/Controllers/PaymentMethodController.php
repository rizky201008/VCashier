<?php

namespace App\Http\Controllers;

use App\Data\PaymentMethodRepository;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;

class PaymentMethodController extends Controller
{
    private $paymentMethodRepository;

    public function __construct()
    {
        $this->paymentMethodRepository = new PaymentMethodRepository();
    }

    public function getAllPaymentMethods(Request $request): JsonResponse
    {
        return response()->json($this->paymentMethodRepository->getAllPaymentMethod());
    }
}
