<?php

namespace App\Http\Controllers;

use App\Models\Transaction;
use Illuminate\Http\Request;
use App\Repository\PaymentRepository;
use Illuminate\Http\JsonResponse;

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
        $paymentRepository = new PaymentRepository();
        return response()->json(
            $paymentRepository->processPayment($request->all())
        );
    }

    public function createVa(Request $request): JsonResponse
    {
        $request->validate([
            'transaction_id' => 'required|exists:transactions,id'
        ]);
        $paymentRepository = new PaymentRepository();
        $va = $paymentRepository->processCreateVa($request->transaction_id);
        return response()->json([
            'message' => 'VA created',
            'va' => $va
        ]);
    }

    public function midtrans(Request $request): JsonResponse
    {
        $data = $request->all();
        $trxId = $data['order_id'];
        $status = $data['transaction_status'];

        $transaction = Transaction::find($trxId);

        switch ($status) {
            case 'settlement':
                $transaction->update([
                    'payment_status' => 'paid',
                    'transaction_status' => 'completed'
                ]);
                break;

            case 'cancel':
            case 'expire':
                $transaction->update([
                    'payment_status' => 'canceled',
                    'transaction_status' => 'canceled'
                ]);
                break;

            default:
                break;
        }

        return response()->json(
            [
                'status' => 'success',
                'message' => 'success'
            ]
        );
    }
}
