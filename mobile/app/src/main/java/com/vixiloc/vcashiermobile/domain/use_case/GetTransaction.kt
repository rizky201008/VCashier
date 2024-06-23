package com.vixiloc.vcashiermobile.domain.use_case

import com.vixiloc.vcashiermobile.commons.HttpHandler
import com.vixiloc.vcashiermobile.commons.Resource
import com.vixiloc.vcashiermobile.data.remote.dto.products.toDomain
import com.vixiloc.vcashiermobile.data.remote.dto.transactions.toDomain
import com.vixiloc.vcashiermobile.domain.model.TransactionResponse
import com.vixiloc.vcashiermobile.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class GetTransaction(
    private val repository: TransactionRepository,
    private val httpHandler: HttpHandler,
    private val token: GetToken
) {
    operator fun invoke(id: String): Flow<Resource<TransactionResponse>> = flow {
        try {
            emit(Resource.Loading())
            val token = token().first()
            val response = repository.getTransaction(token = token, id = id)
            emit(Resource.Success(response.toDomain()))
        } catch (e: HttpException) {
            val errorMessage = httpHandler.handleHttpException(e)
            emit(Resource.Error(errorMessage))
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    e.localizedMessage ?: "Couldn't reach server. Check your internet connection."
                )
            )
        }
    }
}