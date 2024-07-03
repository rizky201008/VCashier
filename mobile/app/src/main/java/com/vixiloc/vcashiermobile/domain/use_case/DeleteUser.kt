package com.vixiloc.vcashiermobile.domain.use_case

import com.vixiloc.vcashiermobile.domain.model.user.DeleteUserRequest
import com.vixiloc.vcashiermobile.domain.model.user.toDto
import com.vixiloc.vcashiermobile.domain.repository.UserRepository
import com.vixiloc.vcashiermobile.utils.HttpHandler
import com.vixiloc.vcashiermobile.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class DeleteUser(
    private val repository: UserRepository,
    private val httpHandler: HttpHandler,
    private val token: GetToken
) {
    operator fun invoke(data: DeleteUserRequest): Flow<Resource<String>> = flow {
        val token = token().first()
        try {
            emit(Resource.Loading())
            val response = repository.deleteUser(token = token, data = data.toDto())
            emit(Resource.Success(response.message))
        } catch (e: HttpException) {
            val errorMessage = httpHandler.handleHttpException(e)
            emit(Resource.Error(errorMessage))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}