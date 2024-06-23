package com.vixiloc.vcashiermobile.domain.use_case

import com.vixiloc.vcashiermobile.commons.HttpHandler
import com.vixiloc.vcashiermobile.commons.Resource
import com.vixiloc.vcashiermobile.data.remote.dto.customers.toCustomerResponse
import com.vixiloc.vcashiermobile.domain.model.CustomerResponseItem
import com.vixiloc.vcashiermobile.domain.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class GetCustomers(
    private val repository: CustomerRepository,
    private val getToken: GetToken,
    private val httpHandler: HttpHandler
) {

    operator fun invoke(): Flow<Resource<List<CustomerResponseItem>>> = flow {
        val token = getToken().first()
        try {
            emit(Resource.Loading())
            val customers = repository.getCustomers(token).toCustomerResponse().data
            emit(Resource.Success(customers))
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