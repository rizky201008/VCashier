package com.vixiloc.vcashiermobile.domain.use_case

import com.vixiloc.vcashiermobile.utils.HttpHandler
import com.vixiloc.vcashiermobile.utils.Resource
import com.vixiloc.vcashiermobile.data.remote.dto.customers.toOnlyResponseMessage
import com.vixiloc.vcashiermobile.domain.model.customers.CreateUpdateCustomerRequest
import com.vixiloc.vcashiermobile.domain.model.OnlyResponseMessage
import com.vixiloc.vcashiermobile.domain.model.customers.toCreateUpdateCustomerRequestDto
import com.vixiloc.vcashiermobile.domain.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class UpdateCustomer(
    private val repository: CustomerRepository,
    private val getToken: GetToken,
    private val httpHandler: HttpHandler
) {

    operator fun invoke(data: CreateUpdateCustomerRequest): Flow<Resource<OnlyResponseMessage>> =
        flow {
            val token: String = getToken().first()
            try {
                emit(Resource.Loading())
                val response = repository.updateCustomer(token, data.toCreateUpdateCustomerRequestDto())
                emit(Resource.Success(response.toOnlyResponseMessage()))
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