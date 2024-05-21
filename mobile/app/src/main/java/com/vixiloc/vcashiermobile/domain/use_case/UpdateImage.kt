package com.vixiloc.vcashiermobile.domain.use_case

import com.vixiloc.vcashiermobile.commons.HttpHandler
import com.vixiloc.vcashiermobile.commons.RequestBodyBuilder
import com.vixiloc.vcashiermobile.commons.Resource
import com.vixiloc.vcashiermobile.data.remote.dto.toDomain
import com.vixiloc.vcashiermobile.domain.model.OnlyResponseMessage
import com.vixiloc.vcashiermobile.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okio.IOException
import retrofit2.HttpException

class UpdateImage(
    private val repository: ProductsRepository,
    private val getToken: GetToken,
    private val httpHandler: HttpHandler
) {
    operator fun invoke(
        productId: String,
        image: MultipartBody.Part?
    ): Flow<Resource<OnlyResponseMessage>> =
        flow {
            val token = getToken().first()
            val productIdRequestBody = RequestBodyBuilder.createString(productId)
            try {
                emit(Resource.Loading())
                image?.let {
                    val response = repository.updateImage(
                        token = token,
                        productId = productIdRequestBody,
                        newImage = image
                    )
                    emit(Resource.Success(response.toDomain()))
                } ?: emit(Resource.Error("Image is null"))
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