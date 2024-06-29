package com.vixiloc.vcashiermobile.presentation.screens.transaction.checkout

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vixiloc.vcashiermobile.utils.Resource
import com.vixiloc.vcashiermobile.domain.model.ValidationRule
import com.vixiloc.vcashiermobile.domain.model.transactions.CartItems
import com.vixiloc.vcashiermobile.domain.model.transactions.CreateTransactionRequest
import com.vixiloc.vcashiermobile.domain.model.transactions.toDataClass
import com.vixiloc.vcashiermobile.domain.use_case.UseCaseManager
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CheckoutScreenViewModel(useCaseManager: UseCaseManager) : ViewModel() {

    private val _state = mutableStateOf(CheckoutScreenState())
    val state: State<CheckoutScreenState> = _state

    val getCartItemsUseCase = useCaseManager.getCartItemsUseCase()
    val updateCartUseCase = useCaseManager.updateCartUseCase()
    val deleteCartUseCase = useCaseManager.deleteCartUseCase()
    val createTransactionUseCase = useCaseManager.createTransactionUseCase()
    val validateInputUseCase = useCaseManager.validateInput()
    val clearCartUseCase = useCaseManager.clearCartUseCase()

    fun onEvent(event: CheckoutScreenEvent) {
        when (event) {

            is CheckoutScreenEvent.UpdateCustomer -> {
                _state.value = state.value.copy(customer = event.customer)
            }

            is CheckoutScreenEvent.UpdateCart -> {
                updateCart(event.cartItems)
            }

            is CheckoutScreenEvent.DeleteCartItem -> {
                deleteCartItem(event.data)
            }

            is CheckoutScreenEvent.CreateTransaction -> {
                validateAllInput()
            }

            is CheckoutScreenEvent.DismissErrorAlert -> {
                _state.value = _state.value.copy(error = "", validationErrors = emptyList())
            }

            is CheckoutScreenEvent.DismissSuccessAlert -> {
                _state.value = _state.value.copy(success = "")
                clearCartItems()
            }

        }
    }

    private fun validateAllInput() {
        val state = state.value
        val validateCartItems = validateInputUseCase(state.cartItems, ValidationRule.NotEmpty)
        val validateCustomer = validateInputUseCase(state.customer, ValidationRule.NotNull)

        val resultError = listOf(
            validateCartItems,
            validateCustomer
        ).any { !it.successful }

        if (resultError) {
            if (validateCustomer.errorMessage != null) {
                _state.value = _state.value.copy(
                    validationErrors = state.validationErrors.plus("Anda wajib memilih 1 pelanggan")
                )
            }

            if (validateCartItems.errorMessage != null) {
                _state.value = _state.value.copy(
                    validationErrors = state.validationErrors.plus("Anda harus memasukkan setidaknya 1 produk dalam keranjang")
                )
            }
        } else {
            createTransaction()
        }
    }

    private fun createTransaction() {
        val state = state.value
        val data = CreateTransactionRequest(
            customerId = state.customer!!.id,
            items = state.cartItems.map { it.toDataClass() }
        )
        viewModelScope.launch {
            createTransactionUseCase(data).onEach { res ->
                when (res) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        _state.value =
                            _state.value.copy(
                                isLoading = false,
                                success = res.data?.message ?: "Sukses menambah transaksi",
                                transactionId = res.data?.transactionId ?: ""
                            )
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = res.message ?: "Error unknown"
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun deleteCartItem(data: CartItems) {
        viewModelScope.launch {
            deleteCartUseCase(data)
        }
    }

    private fun updateCart(cartItems: CartItems) {
        viewModelScope.launch {
            updateCartUseCase(cartItems)
        }
    }

    private fun getCartItems() {
        viewModelScope.launch {
            getCartItemsUseCase().onEach {
                _state.value = state.value.copy(cartItems = it)
            }.stateIn(viewModelScope)
        }
    }

    private fun clearCartItems() {
        viewModelScope.launch {
            clearCartUseCase()
        }
    }

    init {
        getCartItems()
    }
}