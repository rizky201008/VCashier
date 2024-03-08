<?php

namespace App\Data;

use App\Models\Customer;

class CustomerRepository
{
    private Customer $customer;

    public function __construct(Customer $customer)
    {
        $this->customer = $customer;
    }

    public function getCustomers()
    {
        return $this->customer->all();
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
        $product = $this->customer->find($id)->update($data);
        return $product;
    }

    public function deleteCustomer($id)
    {
        return $this->customer->find($id)->delete();
    }
}
