package com.vixiloc.vcashiermobile.domain.use_case

import androidx.datastore.core.IOException
import com.vixiloc.vcashiermobile.data.remote.dto.product_logs.toDomain
import com.vixiloc.vcashiermobile.domain.model.product_logs.CreateProductLogResponse
import com.vixiloc.vcashiermobile.domain.model.product_logs.CreateProductLogsRequest
import com.vixiloc.vcashiermobile.domain.model.product_logs.toDto
import com.vixiloc.vcashiermobile.domain.repository.ProductsRepository
import com.vixiloc.vcashiermobile.utils.HttpHandler
import com.vixiloc.vcashiermobile.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class CreateProductLogs(
    private val repository: ProductsRepository,
    private val token: GetToken,
    private val httpHandler: HttpHandler
) {

    operator fun invoke(data: CreateProductLogsRequest): Flow<Resource<CreateProductLogResponse>> =
        flow {
            val token = token().first()
            emit(Resource.Loading())
            try {
                val response = repository.createProductLogs(token, data.toDto())
                emit(Resource.Success(response.toDomain()))
            } catch (e: HttpException) {
                val errorMessage = httpHandler.handleHttpException(e)
                emit(Resource.Error(errorMessage))
            } catch (e: IOException) {
                emit(
                    Resource.Error(
                        e.localizedMessage
                            ?: "Couldn't reach server. Check your internet connection."
                    )
                )
            }
        }
}