<?php

namespace App\Http\Controllers;

use App\Models\Transaction;
use App\Models\TransactionItem;
use App\Repository\TransactionRepository;
use Carbon\Carbon;
use Exception;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;
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
        return response()->json($this->transactionRepository->getTransactions($status, $paymentStatus));
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

    public function updateTransaction(Request $request): JsonResponse
    {
        $data = $request->all();
        $data['user_id'] = $request->user()->id;
        return response()->json($this->transactionRepository->updateTransaction($data));
    }

    public function transactionReport(): JsonResponse
    {
        $today = Carbon::today();

        // Query untuk menghitung total omzet hari ini
        $todayOmzet = TransactionItem::whereHas('transaction', function ($query) {
            $query->where('transaction_status', 'completed');
        })->whereDate('created_at', $today)->sum('subtotal');

        // Query untuk menghitung total profit hari ini
        $todayProfit = TransactionItem::whereHas('transaction', function ($query) {
            $query->where('transaction_status', 'completed');
        })->whereDate('created_at', $today)->sum('profit');

        // Query untuk mendapatkan 5 transaksi terbaru dengan status completed
        $latestTransaction = Transaction::with(['customer', 'items'])->where('transaction_status', 'completed')
            ->orderBy('created_at', 'desc')
            ->limit(5)
            ->get();

        return response()->json([
            'today_omzet' => $todayOmzet,
            'today_profit' => $todayProfit,
            'latest_transaction' => $latestTransaction
        ]);
    }
}
