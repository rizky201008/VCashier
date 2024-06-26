<?php

namespace App\Http\Controllers;

use App\Repository\TransactionRepository;
use Exception;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Validator;

class TransactionController extends Controller
{
    private TransactionRepository $transactionRepository;

    public function __construct()
    {
        $this->transactionRepository = new TransactionRepository();
    }

    public function getTransactions(Request $request): JsonResponse
    {
        $status = $request->query('status');
        $paymentStatus = $request->query('payment_status');
        return response()->json($this->transactionRepository->getTransactions($status,$paymentStatus));
    }

    public function getTransaction(string $id): JsonResponse
    {
        $validated = Validator::make(['id' => $id], [
            'id' => 'required|exists:transactions,id'
        ]);
        if ($validated->fails()) {
            throw new Exception($validated->messages()->first());
        }

        return response()->json($this->transactionRepository->getTransactionById($id));
    }

    public function createTransaction(Request $request): JsonResponse
    {
        $request->validate([
            'customer_id' => 'required|exists:customers,id',
            'items' => 'required|array'
        ]);

        $data = $request->all();
        $data['user_id'] = $request->user()->id;
        $data['total_amount'] = $this->transactionRepository->getTotalAmount($data['items']);
        $data['transaction_status'] = 'draft';

        return response()->json($this->transactionRepository->processTransaction($data));
    }
}
