package com.vixiloc.vcashiermobile.domain.use_case

import com.vixiloc.vcashiermobile.data.remote.dto.transactions.toDomain
import com.vixiloc.vcashiermobile.data.remote.dto.users.toDomain
import com.vixiloc.vcashiermobile.domain.model.transactions.UpdateTransactionResponse
import com.vixiloc.vcashiermobile.domain.model.user.UpdateUserRequest
import com.vixiloc.vcashiermobile.domain.model.user.UpdateUserResponse
import com.vixiloc.vcashiermobile.domain.model.user.toDto
import com.vixiloc.vcashiermobile.domain.repository.UserRepository
import com.vixiloc.vcashiermobile.utils.HttpHandler
import com.vixiloc.vcashiermobile.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class UpdateUser(
    private val repository: UserRepository,
    private val token: GetToken,
    private val httpHandler: HttpHandler
) {
    operator fun invoke(data: UpdateUserRequest): Flow<Resource<UpdateUserResponse>> = flow {
        val token = token().first()
        try {
            emit(Resource.Loading())
            val response = repository.updateUser(token = token, data = data.toDto())
            emit(Resource.Success(response.toDomain()))
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