package com.vixiloc.vcashiermobile.domain.use_case

import com.vixiloc.vcashiermobile.domain.repository.TransactionRepository

class ClearCart(private val repository: TransactionRepository) {

    suspend operator fun invoke() {
        repository.clearCartItems()
    }
}