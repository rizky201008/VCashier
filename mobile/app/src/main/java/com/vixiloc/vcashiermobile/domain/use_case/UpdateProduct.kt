package com.vixiloc.vcashiermobile.domain.use_case

import com.vixiloc.vcashiermobile.utils.HttpHandler
import com.vixiloc.vcashiermobile.utils.Resource
import com.vixiloc.vcashiermobile.data.remote.dto.products.toDomain
import com.vixiloc.vcashiermobile.domain.model.products.CreateProductRequest
import com.vixiloc.vcashiermobile.domain.model.OnlyResponseMessage
import com.vixiloc.vcashiermobile.domain.model.products.toDto
import com.vixiloc.vcashiermobile.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class UpdateProduct(
    private val repository: ProductsRepository,
    private val getToken: GetToken,
    private val httpHandler: HttpHandler
) {
    operator fun invoke(data: CreateProductRequest): Flow<Resource<OnlyResponseMessage>> =
        flow {
            try {
                val token: String = getToken().first()
                emit(Resource.Loading())
                val response = repository.updateProduct(token, data.toDto())
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