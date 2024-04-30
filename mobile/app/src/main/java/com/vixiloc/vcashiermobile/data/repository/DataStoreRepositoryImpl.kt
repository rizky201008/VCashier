package com.vixiloc.vcashiermobile.data.repository

import com.vixiloc.vcashiermobile.data.local.prefs.UserPreference
import com.vixiloc.vcashiermobile.domain.repository.DataStoreRepository

class DataStoreRepositoryImpl(private val preference: UserPreference) : DataStoreRepository {
    override fun getApiKey() = preference.getApiKey()

    override suspend fun saveApiKey(data: String) = preference.saveApiKey(data)
}