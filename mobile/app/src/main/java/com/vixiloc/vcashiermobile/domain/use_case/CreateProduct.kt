package com.vixiloc.vcashiermobile.domain.use_case

import com.vixiloc.vcashiermobile.commons.HttpHandler
import com.vixiloc.vcashiermobile.commons.Resource
import com.vixiloc.vcashiermobile.data.remote.dto.toDomain
import com.vixiloc.vcashiermobile.domain.model.CreateProductResponse
import com.vixiloc.vcashiermobile.domain.model.CreateUpdateProductRequest
import com.vixiloc.vcashiermobile.domain.model.toDto
import com.vixiloc.vcashiermobile.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class CreateProduct(
    private val productRepository: ProductsRepository,
    private val getToken: GetToken,
    private val httpHandler: HttpHandler
) {
    operator fun invoke(data: CreateUpdateProductRequest): Flow<Resource<CreateProductResponse>> =
        flow {
            val token = getToken().first()
            try {
                emit(Resource.Loading())
                val response = productRepository.createProduct(token = token, data = data.toDto())
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