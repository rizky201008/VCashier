package com.vixiloc.vcashiermobile.domain.use_case

import com.vixiloc.vcashiermobile.data.remote.dto.products.toDomain
import com.vixiloc.vcashiermobile.domain.model.products.UpdateProductVariationRequest
import com.vixiloc.vcashiermobile.domain.model.products.UpdateProductVariationResponse
import com.vixiloc.vcashiermobile.domain.model.products.toDto
import com.vixiloc.vcashiermobile.domain.repository.ProductsRepository
import com.vixiloc.vcashiermobile.utils.HttpHandler
import com.vixiloc.vcashiermobile.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class UpdateProductVariation(
    private val repository: ProductsRepository,
    private val getToken: GetToken,
    private val httpHandler: HttpHandler
) {

    operator fun invoke(data: UpdateProductVariationRequest): Flow<Resource<UpdateProductVariationResponse>> =
        flow {
            try {
                val token: String = getToken().first()
                emit(Resource.Loading())
                val response = repository.updateVariation(token, data.toDto())
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