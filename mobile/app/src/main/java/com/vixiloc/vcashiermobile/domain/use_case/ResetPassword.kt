package com.vixiloc.vcashiermobile.domain.use_case

import com.vixiloc.vcashiermobile.data.remote.dto.auth.toDomain
import com.vixiloc.vcashiermobile.domain.model.auth.ResetPasswordResponse
import com.vixiloc.vcashiermobile.domain.repository.AuthRepository
import com.vixiloc.vcashiermobile.utils.HttpHandler
import com.vixiloc.vcashiermobile.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class ResetPassword(
    private val repository: AuthRepository,
    private val httpHandler: HttpHandler,
    private val token: GetToken
) {
    operator fun invoke(id: String): Flow<Resource<ResetPasswordResponse>> = flow {
        try {
            val token = token().first()
            emit(Resource.Loading())
            val response = repository.resetPassword(token, id)
            emit(Resource.Success(response.toDomain()))
        } catch (e: HttpException) {
            val errorMessage = httpHandler.handleHttpException(e)
            emit(Resource.Error(errorMessage))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}