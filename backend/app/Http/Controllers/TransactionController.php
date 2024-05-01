<?php

namespace App\Http\Controllers;

use App\Data\TransactionRepository;
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

    public function getTransactions(): JsonResponse
    {
        return response()->json(
            $this->transactionRepository->getTransactions()
        );
    }

    public function getTransaction(int $id): JsonResponse
    {
        $validated = Validator::make(['id' => $id], [
            'id' => 'required|exists:transactions,id'
        ]);
        if ($validated->fails()) {
            return response()->json($validated->messages()->first(), 400);
        }

        return response()->json(
            $this->transactionRepository->getTransactionById($id)
        );
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

        DB::beginTransaction();

        try {
            $transaction = $this->transactionRepository->saveTransaction($data);
            $this->transactionRepository->saveTransactionItems($data, $transaction->id);

            DB::commit();

            return response()->json([
                'message' => 'Transaction created'
            ]);
        } catch (\Exception $e) {
            DB::rollBack();
            return response()->json([
                'message' => $e->getMessage()
            ], 500);
        }
    }
}
