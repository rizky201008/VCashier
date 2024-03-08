<?php

namespace App\Http\Controllers;

use App\Data\CustomerRepository;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class CustomerController extends Controller
{
    private CustomerRepository $customerRepository;

    public function __construct(CustomerRepository $customerRepository)
    {
        $this->customerRepository = $customerRepository;
    }

    public function getCustomers(): JsonResponse
    {
        return response()->json($this->customerRepository->getCustomers());
    }

    public function getCustomer($id): JsonResponse
    {
        $validator = Validator::make(['id' => $id], [
            'id' => 'required|integer|exists:customers,id'
        ]);

        if ($validator->fails()) {
            throw new \Exception($validator->errors()->first());
        }
        return response()->json($this->customerRepository->getCustomer($id));
    }

    public function createCustomer(Request $request): JsonResponse
    {
        return response()->json($this->customerRepository->createCustomer($request->all()));
    }

    public function updateCustomer(Request $request, $id): JsonResponse
    {
        $validator = Validator::make(['id' => $id], [
            'id' => 'required|integer|exists:customers,id'
        ]);

        if ($validator->fails()) {
            throw new \Exception($validator->errors()->first());
        }
        return response()->json($this->customerRepository->updateCustomer($id, $request->all()));
    }

    public function deleteCustomer($id): JsonResponse
    {
        return response()->json($this->customerRepository->deleteCustomer($id));
    }
}
