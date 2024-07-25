package com.vixiloc.vcashiermobile.domain.use_case

import com.vixiloc.vcashiermobile.data.remote.dto.auth.toDomain
import com.vixiloc.vcashiermobile.domain.model.auth.LogoutResponse
import com.vixiloc.vcashiermobile.domain.repository.AuthRepository
import com.vixiloc.vcashiermobile.utils.HttpHandler
import com.vixiloc.vcashiermobile.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class Logout(
    private val repository: AuthRepository,
    private val token: GetToken,
    private val saveToken: SaveToken,
    private val saveRole: SaveRole,
    private val httpHandler: HttpHandler
) {
    operator fun invoke(): Flow<Resource<LogoutResponse>> = flow {
        val token = token().first()
        emit(Resource.Loading())
        try {
            val response = repository.logout(token)
            saveToken("")
            saveRole("")
            emit(Resource.Success(response.toDomain()))
        } catch (e: HttpException) {
            val errorMessage = httpHandler.handleHttpException(e)
            emit(Resource.Error(errorMessage))
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    e.localizedMessage ?: "Couldn't reach server. Check your internet connection."
                )
            )
        }
    }
}