package com.vixiloc.vcashiermobile.domain.use_case

import android.util.Log
import com.vixiloc.vcashiermobile.utils.HttpHandler
import com.vixiloc.vcashiermobile.utils.Resource
import com.vixiloc.vcashiermobile.utils.Strings.TAG
import com.vixiloc.vcashiermobile.data.remote.dto.auth.toDomain
import com.vixiloc.vcashiermobile.domain.model.auth.LoginRequest
import com.vixiloc.vcashiermobile.domain.model.auth.LoginResponse
import com.vixiloc.vcashiermobile.domain.model.auth.toLoginRequestDto
import com.vixiloc.vcashiermobile.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class Login(
    private val repository: AuthRepository,
    private val httpHandler: HttpHandler,
    private val saveToken: SaveToken,
    private val saveRole: SaveRole
) {

    operator fun invoke(data: LoginRequest): Flow<Resource<LoginResponse>> = flow {
        try {
            Log.d(TAG, "login invoke: called")
            emit(Resource.Loading())
            val response = repository.login(data = data.toLoginRequestDto())
            saveToken(response.token)
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