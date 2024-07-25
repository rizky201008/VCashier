package com.vixiloc.vcashiermobile.domain.use_case

import com.vixiloc.vcashiermobile.domain.repository.DataStoreRepository

class SaveRole(
    private val repository: DataStoreRepository
) {

    suspend operator fun invoke(role: String) {
        repository.saveRole(role)
    }
}