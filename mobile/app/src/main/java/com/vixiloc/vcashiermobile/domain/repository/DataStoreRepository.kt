package com.vixiloc.vcashiermobile.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    fun getApiKey(): Flow<String>

    suspend fun saveApiKey(data: String)
}