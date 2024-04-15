<?php

namespace App\Http\Controllers;

use App\Data\PaymentRepository;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;

class PaymentMethodController extends Controller
{
    private $paymentMethodRepository;

    public function __construct()
    {
        $this->paymentMethodRepository = new PaymentRepository();
    }

    public function getAllPaymentMethods(Request $request): JsonResponse
    {
        return response()->json($this->paymentMethodRepository->getAllPaymentMethod());
    }
}
