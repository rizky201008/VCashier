package com.vixiloc.vcashiermobile.domain.use_case

import com.vixiloc.vcashiermobile.data.remote.dto.payments.toDomain
import com.vixiloc.vcashiermobile.domain.model.payments.CreateVaRequest
import com.vixiloc.vcashiermobile.domain.model.payments.CreateVaResponse
import com.vixiloc.vcashiermobile.domain.model.payments.toDto
import com.vixiloc.vcashiermobile.domain.repository.PaymentsRepository
import com.vixiloc.vcashiermobile.utils.HttpHandler
import com.vixiloc.vcashiermobile.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class CreateVa(
    private val repository: PaymentsRepository,
    private val getToken: GetToken,
    private val httpHandler: HttpHandler
) {
    operator fun invoke(data: CreateVaRequest): Flow<Resource<CreateVaResponse>> = flow {
        emit(Resource.Loading())
        try {
            val token = getToken().first()
            val response = repository.createVa(token, data.toDto())
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