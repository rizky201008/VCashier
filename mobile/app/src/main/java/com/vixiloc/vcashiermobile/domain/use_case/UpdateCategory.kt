package com.vixiloc.vcashiermobile.domain.use_case

import com.vixiloc.vcashiermobile.commons.Resource
import com.vixiloc.vcashiermobile.data.remote.dto.toOnlyResponseMessage
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
    private val categoryRepository: CategoryRepository,
    private val getToken: GetToken
) {
    operator fun invoke(data: CreateUpdateCategoryRequest): Flow<Resource<OnlyResponseMessage>> =
        flow {
            emit(Resource.Loading())
            try {
                val token = getToken().first()
                val response = categoryRepository.updateCategory(
                    token,
                    data.toCreateUpdateCategoryRequestDto()
                )
                emit(Resource.Success(response.toOnlyResponseMessage()))
            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
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