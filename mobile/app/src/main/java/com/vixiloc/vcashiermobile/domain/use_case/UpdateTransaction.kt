package com.vixiloc.vcashiermobile.domain.use_case

import com.vixiloc.vcashiermobile.data.remote.dto.transactions.toDomain
import com.vixiloc.vcashiermobile.domain.model.transactions.UpdateTransactionRequest
import com.vixiloc.vcashiermobile.domain.model.transactions.UpdateTransactionResponse
import com.vixiloc.vcashiermobile.domain.model.transactions.toDomain
import com.vixiloc.vcashiermobile.domain.model.transactions.toDto
import com.vixiloc.vcashiermobile.domain.repository.TransactionRepository
import com.vixiloc.vcashiermobile.utils.HttpHandler
import com.vixiloc.vcashiermobile.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class UpdateTransaction(
    private val repository: TransactionRepository,
    private val getToken: GetToken,
    private val httpHandler: HttpHandler
) {
    operator fun invoke(data:UpdateTransactionRequest) : Flow<Resource<UpdateTransactionResponse>> = flow {
        val token = getToken().first()
        try {
            emit(Resource.Loading())
            val response = repository.updateTransaction(token, data.toDto())
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