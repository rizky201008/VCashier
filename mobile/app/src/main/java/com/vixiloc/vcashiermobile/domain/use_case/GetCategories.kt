package com.vixiloc.vcashiermobile.domain.use_case

import com.vixiloc.vcashiermobile.commons.Resource
import com.vixiloc.vcashiermobile.data.remote.dto.toCategoriesResponse
import com.vixiloc.vcashiermobile.domain.model.CategoriesResponseItem
import com.vixiloc.vcashiermobile.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class GetCategories(private val repository: CategoryRepository, private val getToken: GetToken) {
    operator fun invoke(): Flow<Resource<List<CategoriesResponseItem>>> =
        flow {
            val token: String = getToken().first()
            try {
                emit(Resource.Loading())
                val categories = repository.getCategories("Bearer $token")
                emit(Resource.Success(categories.toCategoriesResponse().categoriesResponseDto))
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