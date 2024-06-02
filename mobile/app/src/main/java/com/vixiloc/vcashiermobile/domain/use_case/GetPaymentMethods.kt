package com.vixiloc.vcashiermobile.domain.use_case

import com.vixiloc.vcashiermobile.commons.HttpHandler
import com.vixiloc.vcashiermobile.commons.Resource
import com.vixiloc.vcashiermobile.data.remote.dto.toDomain
import com.vixiloc.vcashiermobile.domain.model.PaymentMethods
import com.vixiloc.vcashiermobile.domain.repository.PaymentsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class GetPaymentMethods(
    private val repository: PaymentsRepository,
    private val token: GetToken,
    private val httpHandler: HttpHandler
) {
    operator fun invoke(): Flow<Resource<PaymentMethods>> = flow {
        try {
            val token = token().first()
            emit(Resource.Loading())
            val paymentMethods = repository.getPaymentMethods(token)
            emit(Resource.Success(paymentMethods.toDomain()))
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