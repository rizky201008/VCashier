<?php

namespace App\Data;

use App\Models\Customer;

class CustomerRepository
{
    private Customer $customer;

    public function __construct()
    {
        $this->customer = new Customer();
    }

    public function getCustomers()
    {
        return response()->json(["data" => $this->customer->all()]);
    }

    public function getCustomer($id)
    {
        return $this->customer->find($id);
    }

    public function createCustomer($data)
    {
        $this->customer->create($data);
        return response()->json(["message" => "Customer created"], 201);
    }

    public function updateCustomer($id, $data)
    {
        $this->customer->find($id)->update($data);
        return response()->json(["message" => "product updated!"]);
    }

    public function deleteCustomer($id)
    {
        return $this->customer->find($id)->delete();
    }
}
