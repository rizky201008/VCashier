package com.vixiloc.vcashiermobile.domain.use_case

import android.util.Log
import com.vixiloc.vcashiermobile.commons.HttpHandler
import com.vixiloc.vcashiermobile.commons.Resource
import com.vixiloc.vcashiermobile.commons.Strings.TAG
import com.vixiloc.vcashiermobile.data.remote.dto.toLoginResponse
import com.vixiloc.vcashiermobile.domain.model.LoginRequest
import com.vixiloc.vcashiermobile.domain.model.LoginResponse
import com.vixiloc.vcashiermobile.domain.model.toLoginRequestDto
import com.vixiloc.vcashiermobile.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class Login(private val repository: AuthRepository, private val httpHandler: HttpHandler) {

    operator fun invoke(data: LoginRequest): Flow<Resource<LoginResponse>> = flow {
        try {
            Log.d(TAG, "login invoke: called")
            emit(Resource.Loading())
            val response = repository.login(data = data.toLoginRequestDto())
            emit(Resource.Success(response.toLoginResponse()))
        } catch (e: HttpException) {
            val errorMessage = httpHandler.handleHttpException(e)
            emit(Resource.Error(errorMessage))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}