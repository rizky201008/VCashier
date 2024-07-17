package com.vixiloc.vcashiermobile.presentation.screens.product_log.add_logs

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vixiloc.vcashiermobile.domain.model.product_logs.CreateProductLogsRequest
import com.vixiloc.vcashiermobile.domain.use_case.UseCaseManager
import com.vixiloc.vcashiermobile.utils.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class AddProductLogViewModel(useCaseManager: UseCaseManager) : ViewModel() {

    private var job: Job? = null
    val getProductsUseCase = useCaseManager.getProductsUseCase()
    val validateNotBlankUseCase = useCaseManager.validateNotBlankUseCase()
    val validateIsNumberUseCase = useCaseManager.validateIsNumberUseCase()
    val createProductLogsUseCase = useCaseManager.createProductLogsUseCase()
    private val _state = mutableStateOf(AddProductLogState())
    val state: State<AddProductLogState> = _state

    fun onEvent(event: AddProductLogEvent) {
        when (event) {
            is AddProductLogEvent.ChangeInput -> {
                _state.value = when (event.name) {
                    InputName.Info -> {
                        state.value.copy(logInfo = event.value)
                    }

                    InputName.Amount -> {
                        state.value.copy(logAmount = event.value)
                    }

                    InputName.Type -> {
                        state.value.copy(logType = event.value)
                    }
                }
            }

            is AddProductLogEvent.AddProductLog -> {
                validateAddProductLog()
            }

            is AddProductLogEvent.SelectVariation -> {
                _state.value = state.value.copy(
                    logProductVariation = event.variation
                )
            }

            is AddProductLogEvent.ShowAddLogDialog -> {
                _state.value = state.value.copy(
                    showAddLogDialog = event.show
                )
                if (!event.show) {
                    clearInputs()
                }
            }

            is AddProductLogEvent.SearchProduct -> {
                job?.cancel()
                _state.value = state.value.copy(searchQuery = event.query)

                searchProduct(event.query)
            }
        }
    }

    private fun searchProduct(query: String) {
        job = viewModelScope.launch {
            delay(500)
            getProducts()
            _state.value = state.value.copy(
                products = state.value.products.filter {
                    it.name.contains(query, ignoreCase = true)
                }
            )
        }
    }

    private fun clearInputs() {
        _state.value = state.value.copy(
            logInfo = "",
            logAmount = "",
            logType = "increase",
            logProductVariation = null
        )
    }

    private fun validateAddProductLog() {
        val validatedlogInfo = validateNotBlankUseCase(state.value.logInfo)
        val validatedlogAmount = validateNotBlankUseCase(state.value.logAmount)
        val validatedLogAmount1 = validateIsNumberUseCase(state.value.logAmount)

        val hasError = listOf(
            validatedlogInfo,
            validatedlogAmount,
            validatedLogAmount1
        ).any { !it.successful }

        if (hasError) {
            _state.value = state.value.copy(
                logInfoError = validatedlogInfo.errorMessage ?: "",
                logAmountError = validatedlogAmount.errorMessage ?: "",
            )
            return
        }
        addProductLog()
    }

    private fun addProductLog() {
        val logInfo = state.value.logInfo
        val logAmount = state.value.logAmount
        val logType = state.value.logType
        val logProductVariation = state.value.logProductVariation

        val data = CreateProductLogsRequest(
            information = logInfo,
            amount = logAmount.toInt(),
            type = logType,
            product_variation_id = logProductVariation!!.id
        )

        createProductLogsUseCase(data).onEach { res ->
            when (res) {
                is Resource.Success -> {
                    _state.value = state.value.copy(isLoading = false, showAddLogDialog = false)
                    clearInputs()
                    getProducts()
                }

                is Resource.Error -> {
                    _state.value = state.value.copy(
                        error = res.message ?: "",
                        showError = true,
                        isLoading = false
                    )
                }

                is Resource.Loading -> {
                    _state.value = state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private suspend fun getProducts() {
        getProductsUseCase().collect { res ->
            when (res) {
                is Resource.Success -> {
                    _state.value =
                        state.value.copy(products = res.data ?: emptyList(), isLoading = false)
                }

                is Resource.Error -> {
                    _state.value = state.value.copy(
                        error = res.message ?: "",
                        showError = true,
                        isLoading = false
                    )
                }

                is Resource.Loading -> {
                    _state.value = state.value.copy(isLoading = true)
                }
            }
        }
    }

    init {
        viewModelScope.launch {
            getProducts()
        }
    }
}