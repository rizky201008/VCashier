package com.vixiloc.vcashiermobile.domain.use_case

import com.vixiloc.vcashiermobile.utils.HttpHandler
import com.vixiloc.vcashiermobile.utils.Resource
import com.vixiloc.vcashiermobile.data.remote.dto.transactions.toDomain
import com.vixiloc.vcashiermobile.domain.model.transactions.TransactionsResponse
import com.vixiloc.vcashiermobile.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class GetTransactions(
    private val repository: TransactionRepository,
    private val httpHandler: HttpHandler,
    private val token: GetToken
) {

    operator fun invoke(
        status: String = "",
        paymentStatus: String = ""
    ): Flow<Resource<TransactionsResponse>> = flow {
        try {
            emit(Resource.Loading())
            val token = token().first()
            val response = repository.getTransactions(token, status, paymentStatus)
            emit(Resource.Success(response.toDomain()))
        } catch (e: HttpException) {
            val errorMessage = httpHandler.handleHttpException(e)
            emit(Resource.Error(errorMessage))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}