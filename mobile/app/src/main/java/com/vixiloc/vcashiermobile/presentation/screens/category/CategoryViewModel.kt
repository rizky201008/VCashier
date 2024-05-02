package com.vixiloc.vcashiermobile.presentation.screens.category

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vixiloc.vcashiermobile.commons.Resource
import com.vixiloc.vcashiermobile.domain.use_case.GetCategories
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CategoryViewModel(private val getCategories: GetCategories) : ViewModel() {

    var state by mutableStateOf(CategoryState())

    fun onEvent(e: CategoryEvent) {
        when (e) {
            is CategoryEvent.DismissAlertMessage -> {
                state = state.copy(error = "")
            }
        }
    }

    init {
        getAllCategories()
    }

    private fun getAllCategories() {
        getCategories().onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    state = state.copy(isLoading = true)
                }

                is Resource.Error -> {
                    state = state.copy(
                        error = resource.message ?: "An error unexpected!",
                        isLoading = false
                    )
                }

                is Resource.Success -> {
                    state = state.copy(isLoading = false, categories = resource.data ?: emptyList())
                }

            }
        }.launchIn(viewModelScope)
    }
}