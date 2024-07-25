package com.vixiloc.vcashiermobile.domain.use_case

import com.vixiloc.vcashiermobile.domain.repository.DataStoreRepository

class GetRole(
    private val repository: DataStoreRepository
) {
    operator fun invoke() = repository.getRole()
}