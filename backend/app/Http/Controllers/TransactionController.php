<?php

namespace App\Http\Controllers;

use App\Data\TransactionRepository;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;

class TransactionController extends Controller
{
    private TransactionRepository $transactionRepository;

    public function __construct()
    {
        $this->transactionRepository = new TransactionRepository();
    }

    public function getTransactions(): JsonResponse
    {
        return response()->json(
            $this->transactionRepository->getTransactions()
        );
    }

    public function getTransaction(int $id): JsonResponse
    {
        return response()->json(
            $this->transactionRepository->getTransaction($id)
        );
    }
    public function createTransaction(Request $request): JsonResponse
    {
        $request->validate([
            'customer_id' => 'required|integer',
            'items' => 'required|array'
        ]);

        $request->merge([
            'user_id' => $request->user()->id,
        ]);

        return response()->json(
            $this->transactionRepository->createTransaction($request->all()), 201
        );
    }
}
