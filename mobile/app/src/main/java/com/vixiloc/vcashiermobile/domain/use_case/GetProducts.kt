package com.vixiloc.vcashiermobile.domain.use_case

import android.util.Log
import com.vixiloc.vcashiermobile.commons.Resource
import com.vixiloc.vcashiermobile.commons.Strings.TAG
import com.vixiloc.vcashiermobile.data.remote.dto.toProductResponseItems
import com.vixiloc.vcashiermobile.domain.model.ProductResponseItems
import com.vixiloc.vcashiermobile.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class GetProducts(private val repository: ProductsRepository, private val getToken: GetToken) {

    operator fun invoke(): Flow<Resource<List<ProductResponseItems>>> = flow {
        Log.d(TAG, "invoke: dipanggil")
        try {
            emit(Resource.Loading())
            val token: String = getToken().first()
            val response = repository.getProducts(token).data

            val data = response.map {
                it.toProductResponseItems()
            }

            emit(Resource.Success(data))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    e.localizedMessage ?: "Couldn't reach server. Check your internet connection."
                )
            )
        }
    }
}