package com.vixiloc.vcashiermobile.domain.use_case

import com.vixiloc.vcashiermobile.commons.HttpHandler
import com.vixiloc.vcashiermobile.commons.Resource
import com.vixiloc.vcashiermobile.domain.model.OnlyResponseMessage
import com.vixiloc.vcashiermobile.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import okhttp3.RequestBody
import okio.IOException
import retrofit2.HttpException

class UpdateImage(
    private val productRepository: ProductsRepository,
    private val getToken: GetToken,
    private val httpHandler: HttpHandler
) {
    operator fun invoke(data: RequestBody): Flow<Resource<OnlyResponseMessage>> =
        flow {
            val token = getToken().first()
            try {
                emit(Resource.Loading())
//                val response = productRepository.updateImage(token = token, data = data.toDto(),image = )
//                emit(Resource.Success(response.toDomain()))
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