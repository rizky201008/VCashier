<?php

namespace App\Http\Controllers;

use App\Data\PaymentRepository;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;

class PaymentController extends Controller
{
    private PaymentRepository $paymentRepository;

    public function __construct()
    {
        $this->paymentRepository = new PaymentRepository();
    }

    public function getPaymentMethods(): JsonResponse
    {
        return response()->json([
            'data' => $this->paymentRepository->getAllPaymentMethod()
        ]);
    }

    public function createTransactionPayment(Request $request): JsonResponse
    {
        $request->validate([
            'transaction_id' => 'required|exists:transactions,id',
            'payment_method_id' => 'required|exists:payment_methods,id',
            'payment_amount' => 'required'
        ]);
        return $this->paymentRepository->createTransactionPayment($request);
    }
}
