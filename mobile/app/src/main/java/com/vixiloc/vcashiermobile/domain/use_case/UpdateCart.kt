package com.vixiloc.vcashiermobile.domain.use_case

import com.vixiloc.vcashiermobile.domain.model.transactions.CartItems
import com.vixiloc.vcashiermobile.domain.repository.TransactionRepository

class UpdateCart(val repository: TransactionRepository) {
    suspend operator fun invoke(data: CartItems) {
        repository.updateCartItems(data)
    }
}