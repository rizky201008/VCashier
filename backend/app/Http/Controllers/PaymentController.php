<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Data\PaymentRepository;
use Illuminate\Http\JsonResponse;
use Illuminate\Support\Facades\DB;
use App\Data\TransactionRepository;

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
        try {
            DB::beginTransaction();
            $data = $request->all();
            $this->paymentRepository->insertTransactionPayment($data);
            $repo = new TransactionRepository();
            $transaction = $repo->getTransactionById($data['transaction_id']);
            $va = $this->paymentRepository->createVa($transaction);
            $transaction->update([
                'va_number' => $va
            ]);
            DB::commit();
            return response()->json([
                'message' => 'Make payment success'
            ]);
        } catch (\Exception $th) {
            DB::rollBack();
            return response()->json([
                'message' => $th->getMessage()
            ], 500);
        }
    }
}
