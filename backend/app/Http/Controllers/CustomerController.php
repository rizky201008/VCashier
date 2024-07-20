<?php

namespace App\Http\Controllers;

use App\Repository\CustomerRepository;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class CustomerController extends Controller
{
    private CustomerRepository $customerRepository;

    public function __construct()
    {
        $this->customerRepository = new CustomerRepository();
    }

    public function getCustomers(): JsonResponse
    {
        return $this->customerRepository->getCustomers();
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
        $request->validate(
            ['name' => 'required|string|max:255']
        );
        return $this->customerRepository->createCustomer($request->all());
    }

    public function updateCustomer(Request $request): JsonResponse
    {
        $request->validate([
            'id' => 'required|integer|exists:customers,id',
        ]);

        if ($request->has('name')) {
            $request->validate(['name' => 'required|string|max:255']);
        }

        return $this->customerRepository->updateCustomer($request->id, $request->all());
    }
}
