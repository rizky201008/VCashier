package com.vixiloc.vcashiermobile.domain.use_case

import com.vixiloc.vcashiermobile.domain.model.transactions.CartItems
import com.vixiloc.vcashiermobile.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow

class GetCartItems(private val repository: TransactionRepository) {
    suspend operator fun invoke(): Flow<List<CartItems>> {
        return repository.getCartItems()
    }

}