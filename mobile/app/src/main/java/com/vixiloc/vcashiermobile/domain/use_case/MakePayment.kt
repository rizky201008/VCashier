package com.vixiloc.vcashiermobile.domain.use_case

import com.vixiloc.vcashiermobile.utils.HttpHandler
import com.vixiloc.vcashiermobile.utils.Resource
import com.vixiloc.vcashiermobile.data.remote.dto.payments.toDomain
import com.vixiloc.vcashiermobile.domain.model.payments.MakePaymentRequest
import com.vixiloc.vcashiermobile.domain.model.OnlyResponseMessage
import com.vixiloc.vcashiermobile.domain.model.payments.toDto
import com.vixiloc.vcashiermobile.domain.repository.PaymentsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class MakePayment(
    private val repository: PaymentsRepository,
    private val httpHandler: HttpHandler,
    private val token: GetToken
) {
    operator fun invoke(data: MakePaymentRequest): Flow<Resource<OnlyResponseMessage>> = flow {
        try {
            emit(Resource.Loading())
            val token = token().first()
            val response = repository.makePayment(token, data.toDto())
            emit(Resource.Success(response.toDomain()))
        } catch (e: HttpException) {
            val errorMessage = httpHandler.handleHttpException(e)
            emit(Resource.Error(errorMessage))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}