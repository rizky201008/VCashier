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
        $product = $this->customer->create($data);
        return $product;
    }

    public function updateCustomer($id, $data)
    {
        $this->customer->find($id)->update($data);
        return ["message" => "product updated!"];
    }

    public function deleteCustomer($id)
    {
        return $this->customer->find($id)->delete();
    }
}
