package com.vixiloc.vcashiermobile.domain.use_case

import com.vixiloc.vcashiermobile.commons.HttpHandler
import com.vixiloc.vcashiermobile.commons.Resource
import com.vixiloc.vcashiermobile.data.remote.dto.categories.toOnlyResponseMessage
import com.vixiloc.vcashiermobile.domain.model.CreateUpdateCategoryRequest
import com.vixiloc.vcashiermobile.domain.model.OnlyResponseMessage
import com.vixiloc.vcashiermobile.domain.model.toCreateUpdateCategoryRequestDto
import com.vixiloc.vcashiermobile.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class UpdateCategory(
    private val repository: CategoryRepository,
    private val getToken: GetToken,
    private val httpHandler: HttpHandler
) {
    operator fun invoke(data: CreateUpdateCategoryRequest): Flow<Resource<OnlyResponseMessage>> =
        flow {
            emit(Resource.Loading())
            try {
                val token = getToken().first()
                val response = repository.updateCategory(
                    token,
                    data.toCreateUpdateCategoryRequestDto()
                )
                emit(Resource.Success(response.toOnlyResponseMessage()))
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