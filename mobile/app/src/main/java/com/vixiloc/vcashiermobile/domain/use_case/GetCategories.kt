package com.vixiloc.vcashiermobile.domain.use_case

import com.vixiloc.vcashiermobile.commons.HttpHandler
import com.vixiloc.vcashiermobile.commons.Resource
import com.vixiloc.vcashiermobile.data.remote.dto.categories.toCategoriesResponse
import com.vixiloc.vcashiermobile.domain.model.CategoriesResponseItem
import com.vixiloc.vcashiermobile.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class GetCategories(
    private val repository: CategoryRepository,
    private val getToken: GetToken,
    private val httpHandler: HttpHandler
) {
    operator fun invoke(): Flow<Resource<List<CategoriesResponseItem>>> =
        flow {
            val token: String = getToken().first()
            try {
                emit(Resource.Loading())
                val categories = repository.getCategories(token)
                emit(Resource.Success(categories.toCategoriesResponse().categoriesResponseDto))
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