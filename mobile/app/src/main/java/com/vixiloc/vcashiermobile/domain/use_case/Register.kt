package com.vixiloc.vcashiermobile.domain.use_case

import android.util.Log
import com.vixiloc.vcashiermobile.data.remote.dto.auth.toDomain
import com.vixiloc.vcashiermobile.domain.model.auth.RegisterRequest
import com.vixiloc.vcashiermobile.domain.model.auth.RegisterResponse
import com.vixiloc.vcashiermobile.domain.model.auth.toDto
import com.vixiloc.vcashiermobile.domain.repository.AuthRepository
import com.vixiloc.vcashiermobile.utils.HttpHandler
import com.vixiloc.vcashiermobile.utils.Resource
import com.vixiloc.vcashiermobile.utils.Strings.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class Register(
    private val repository: AuthRepository,
    private val httpHandler: HttpHandler,
    private val saveToken: SaveToken
) {

    operator fun invoke(data: RegisterRequest): Flow<Resource<RegisterResponse>> = flow {
        try {
            Log.d(TAG, "login invoke: called")
            emit(Resource.Loading())
            val response = repository.register(data = data.toDto())
            response.token?.let { saveToken(it) }
            emit(Resource.Success(response.toDomain()))
        } catch (e: HttpException) {
            val errorMessage = httpHandler.handleHttpException(e)
            emit(Resource.Error(errorMessage))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}