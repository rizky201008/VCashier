package com.vixiloc.vcashiermobile.domain.use_case

import com.vixiloc.vcashiermobile.data.remote.dto.users.toDomain
import com.vixiloc.vcashiermobile.domain.model.user.UsersResponse
import com.vixiloc.vcashiermobile.domain.repository.UserRepository
import com.vixiloc.vcashiermobile.utils.HttpHandler
import com.vixiloc.vcashiermobile.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class GetUsers(
    private val repository: UserRepository,
    private val httpHandler: HttpHandler,
    private val token: GetToken
) {
    operator fun invoke(): Flow<Resource<UsersResponse>> = flow {
        emit(Resource.Loading())
        try {
            val token = token().first()
            val response = repository.getUsers(token)
            emit(Resource.Success(response.toDomain()))
        } catch (e: HttpException) {
            val errorMessage = httpHandler.handleHttpException(e)
            emit(Resource.Error(errorMessage))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}