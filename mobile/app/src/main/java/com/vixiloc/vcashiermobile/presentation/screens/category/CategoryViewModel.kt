package com.vixiloc.vcashiermobile.presentation.screens.category

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vixiloc.vcashiermobile.utils.Resource
import com.vixiloc.vcashiermobile.utils.Strings.TAG
import com.vixiloc.vcashiermobile.domain.model.categories.CreateUpdateCategoryRequest
import com.vixiloc.vcashiermobile.domain.use_case.CreateCategory
import com.vixiloc.vcashiermobile.domain.use_case.GetCategories
import com.vixiloc.vcashiermobile.domain.use_case.UpdateCategory
import com.vixiloc.vcashiermobile.domain.use_case.UseCaseManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CategoryViewModel(
    useCaseManager: UseCaseManager,
) : ViewModel() {

    private var searchJob: Job? = null

    var state by mutableStateOf(CategoryState())
    private val getCategories: GetCategories = useCaseManager.getCategoriesUseCase()
    private val createCategory: CreateCategory = useCaseManager.createCategoryUseCase()
    private val updateCategory: UpdateCategory = useCaseManager.updateCategoryUseCase()
    private val validateNotBlankUseCase = useCaseManager.validateNotBlankUseCase()

    fun onEvent(e: CategoryEvent) {
        when (e) {
            is CategoryEvent.DismissAlertMessage -> {
                getAllCategories()
                state = state.copy(
                    error = "",
                    success = "",
                    categoryId = null
                )
            }

            is CategoryEvent.InputCategoryName -> {
                state = state.copy(categoryName = e.name)
            }

            is CategoryEvent.SubmitCreateCategory -> {
                validateInput(false)
            }

            is CategoryEvent.PreFillFormData -> {
                state = state.copy(
                    categoryName = e.data.name,
                    categoryId = e.data.id
                )
            }

            is CategoryEvent.SubmitUpdateCategory -> {
                validateInput(true)
            }

            is CategoryEvent.SelectCategory -> {
                state = state.copy(categoryId = e.data?.id)
            }

            is CategoryEvent.InputSearchValue -> {
                searchJob?.cancel()
                state = state.copy(
                    searchQuery = e.query
                )
                searchJob = viewModelScope.launch {
                    delay(1000)
                    searchCategories()
                }
            }

            is CategoryEvent.ShowCreateModal -> {
                state = state.copy(showCreateModal = e.show)
                if (!e.show) {
                    clearInput()
                }
            }

            is CategoryEvent.ShowUpdateModal -> {
                state = state.copy(showUpdateModal = e.show)
                if (!e.show) {
                    clearInput()
                }
            }
        }
    }

    private fun validateInput(update: Boolean) {
        val validatedName = validateNotBlankUseCase(state.categoryName)
        val hasError = listOf(
            validatedName
        ).any { !it.successful }
        if (hasError) {
            state = state.copy(
                categoryNameError = validatedName.errorMessage ?: ""
            )
            return
        }
        if (update) {
            submitUpdateCategory()
        } else {
            submitCreateCategory()
        }
    }

    private fun searchCategories() {
        val query = state.searchQuery
        if (query.isBlank()) {
            getAllCategories()
        } else {
            state = state.copy(
                categories = state.categories.filter {
                    it.name.contains(query, ignoreCase = true)
                }
            )
        }
    }

    private fun submitUpdateCategory() {
        updateCategory(
            data = CreateUpdateCategoryRequest(
                name = state.categoryName,
                id = state.categoryId
            )
        ).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    state = state.copy(isLoading = true)
                }

                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        error = resource.message ?: "An error unexpected!"
                    )
                }

                is Resource.Success -> {
                    state = state.copy(
                        isLoading = false,
                        success = resource.data?.message ?: "Sukses mengubah data",
                        showUpdateModal = false,
                    )
                    clearInput()
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun submitCreateCategory() {
        createCategory(data = CreateUpdateCategoryRequest(name = state.categoryName)).onEach { resource ->
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
                    state =
                        state.copy(
                            isLoading = false,
                            success = resource.data?.message ?: "Success",
                            showCreateModal = false
                        )
                    clearInput()
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getAllCategories() {
        Log.d(TAG, "getAllCategories: called")
        getCategories().onEach { resource ->
            Log.d(TAG, "getAllCategories: ${resource.data}")
            when (resource) {
                is Resource.Loading -> {
                    state = state.copy(isLoading = true, categories = emptyList())
                }

                is Resource.Error -> {
                    state = state.copy(
                        error = resource.message ?: "An error unexpected!",
                        isLoading = false
                    )
                }

                is Resource.Success -> {
                    Log.d(TAG, "getAllCategories: ${resource.data}")
                    state = state.copy(isLoading = false, categories = resource.data ?: emptyList())
                }

            }
        }.launchIn(viewModelScope)
    }

    private fun clearInput() {
        state = state.copy(
            categoryName = "",
            categoryId = null,
            categoryNameError = ""
        )
    }

    init {
        getAllCategories()
    }
}