package com.vixiloc.vcashiermobile.presentation.screens.transaction

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vixiloc.vcashiermobile.commons.Resource
import com.vixiloc.vcashiermobile.commons.Strings
import com.vixiloc.vcashiermobile.commons.Strings.TAG
import com.vixiloc.vcashiermobile.domain.use_case.GetProducts
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.math.log

class TransactionViewModel(private val getProductsUseCase: GetProducts) : ViewModel() {

    var state by mutableStateOf(TransactionState())

    init {
        getProducts()
    }

    fun onEvent(event: TransactionEvent) {
        when (event) {
            is TransactionEvent.ToggleError -> {
                toggleError(event.error)
            }
        }
    }

    private fun getProducts() {
        getProductsUseCase().onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    toggleLoading()
                }

                is Resource.Success -> {
                    toggleLoading()
                    Log.d(TAG, "getProducts: ${resource.data}")
                    state = state.copy(products = resource.data ?: emptyList())
                }

                is Resource.Error -> {
                    toggleLoading()
                    toggleError(resource.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun toggleLoading() {
        state = state.copy(isLoading = !state.isLoading)
    }

    private fun toggleError(error: String) {
        state = state.copy(error = error)
    }
}