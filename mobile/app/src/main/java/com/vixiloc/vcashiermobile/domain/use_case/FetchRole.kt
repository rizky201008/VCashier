package com.vixiloc.vcashiermobile.domain.use_case

import com.vixiloc.vcashiermobile.data.remote.dto.users.toDomain
import com.vixiloc.vcashiermobile.domain.model.user.GetRoleResponse
import com.vixiloc.vcashiermobile.domain.repository.UserRepository
import com.vixiloc.vcashiermobile.utils.HttpHandler
import com.vixiloc.vcashiermobile.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class FetchRole(
    private val repository: UserRepository,
    private val httpHandler: HttpHandler,
    private val getToken: GetToken,
    private val saveRole: SaveRole
) {
    operator fun invoke(): Flow<Resource<GetRoleResponse>> = flow {
        val token = getToken().first()

        try {
            emit(Resource.Loading())
            val response = repository.getRole(token)
            saveRole(response.role)
            emit(Resource.Success(response.toDomain()))
        } catch (e: HttpException) {
            val errorMessage = httpHandler.handleHttpException(e)
            emit(Resource.Error(errorMessage))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}