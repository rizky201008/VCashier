package com.vixiloc.vcashiermobile.presentation.screens.products

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vixiloc.vcashiermobile.commons.Resource
import com.vixiloc.vcashiermobile.domain.use_case.GetProducts
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ProductsViewModel(private val getProducts: GetProducts) : ViewModel() {
    var state by mutableStateOf(ProductState())

    fun onEvent(e:ProductEvent) {
        when(e) {
            is ProductEvent.DismissAlertMessage -> {
                state = state.copy(
                    success = "",
                    error = ""
                )
            }
        }
    }

    fun processGetProducts() {
        getProducts().onEach { res ->
            when(res) {
                is Resource.Loading -> {
                    state = state.copy(isLoading = true)
                }

                is Resource.Success -> {
                    state = state.copy(
                        isLoading = false,
                        products = res.data ?: emptyList()
                    )
                }

                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        error = res.message ?: "An unexpected error occurred"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}