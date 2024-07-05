<?php

namespace App\Repository;

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
        return response()->json(["message" => "customer created"], 201);
    }

    public function updateCustomer($id, $data)
    {
        $this->customer->find($id)->update($data);
        return response()->json(["message" => "customer updated!"]);
    }

    public function deleteCustomer($id)
    {
        $this->customer->find($id)->delete();
        return response()->json(["message" => "customer deleted!"]);
    }
}
