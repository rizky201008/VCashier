package com.vixiloc.vcashiermobile.domain.use_case

import com.vixiloc.vcashiermobile.domain.repository.DataStoreRepository

class SaveToken(private val repository: DataStoreRepository) {

    suspend operator fun invoke(token: String) {
        repository.saveApiKey(token)
    }
}