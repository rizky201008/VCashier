package com.vixiloc.vcashiermobile.domain.use_case

import com.vixiloc.vcashiermobile.data.remote.dto.transactions.toDomain
import com.vixiloc.vcashiermobile.domain.model.transactions.ReportsResponse
import com.vixiloc.vcashiermobile.domain.repository.TransactionRepository
import com.vixiloc.vcashiermobile.utils.HttpHandler
import com.vixiloc.vcashiermobile.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class TransactionReport(
    private val repository: TransactionRepository,
    private val token: GetToken,
    private val httpHandler: HttpHandler
) {
    operator fun invoke(): Flow<Resource<ReportsResponse>> = flow {
        emit(Resource.Loading())
        try {
            val token = token().first()
            val response = repository.reportTransaction(token)
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