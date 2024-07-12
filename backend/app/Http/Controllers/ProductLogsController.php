<?php

namespace App\Http\Controllers;

use App\Repository\ProductLogsRepository;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;

class ProductLogsController extends Controller
{
    private ProductLogsRepository $productLogsRepository;

    public function __construct()
    {
        $this->productLogsRepository = new ProductLogsRepository();
    }

    public function getAllLogs(): JsonResponse
    {
        return response()->json([
            'data' => $this->productLogsRepository->getAllLogs()
        ]);
    }

    public function addLog(Request $request): JsonResponse
    {
        $request->validate([
            'information' => 'required|string',
            'type' => 'required|in:increase,decrease',
            'amount' => 'required|integer',
            'product_variation_id' => 'required|integer',
        ]);

        $data = $request->all();
        $data['user_id'] = $request->user()->id;

        $this->productLogsRepository->addLog($data);

        return response()->json(
            [
                'message' => 'Log added successfully'
            ]
        );
    }

}
