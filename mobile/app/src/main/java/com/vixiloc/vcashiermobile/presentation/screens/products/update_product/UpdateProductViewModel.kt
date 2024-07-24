package com.vixiloc.vcashiermobile.presentation.screens.products.update_product

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vixiloc.vcashiermobile.domain.model.products.UpdateProductRequest
import com.vixiloc.vcashiermobile.domain.model.products.UpdateProductVariationRequest
import com.vixiloc.vcashiermobile.domain.use_case.UseCaseManager
import com.vixiloc.vcashiermobile.utils.FileConverter
import com.vixiloc.vcashiermobile.utils.Resource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class UpdateProductViewModel(ucm: UseCaseManager, private val fileConverter: FileConverter) :
    ViewModel() {
    private val _state = mutableStateOf(UpdateProductState())
    val state: State<UpdateProductState> = _state

    val updateProductUseCase = ucm.updateProductUseCase()
    val updateImageUseCase = ucm.updateImageUseCase()
    val updateProductVariationUseCase = ucm.updateProductVariationUseCase()
    val getProductUseCase = ucm.getProductUseCase()
    val getCategoriesUseCase = ucm.getCategoriesUseCase()

    fun onEvent(event: UpdateProductEvent) {
        when (event) {
            is UpdateProductEvent.SetProductId -> {
                _state.value = state.value.copy(productId = event.id)
            }

            is UpdateProductEvent.ChangeInput -> {
                when (event.name) {
                    FormInputName.ProductName -> {
                        _state.value = state.value.copy(productName = event.value)
                    }

                    FormInputName.ProductDescription -> {
                        _state.value = state.value.copy(productDescription = event.value)
                    }

                    FormInputName.VariationName -> {
                        _state.value = state.value.copy(variationName = event.value)
                    }

                    FormInputName.VariationPrice -> {
                        _state.value = state.value.copy(variationPrice = event.value)
                    }

                    FormInputName.VariationPriceGrocery -> {
                        _state.value = state.value.copy(variationPriceGrocery = event.value)
                    }

                    FormInputName.VariationPriceCapital -> {
                        _state.value = state.value.copy(variationPriceCapital = event.value)
                    }
                }
            }

            is UpdateProductEvent.ShowEditVariation -> {
                _state.value = state.value.copy(showEditVariation = event.show)
            }

            is UpdateProductEvent.SaveEditProduct -> {
                updateProduct()
            }

            is UpdateProductEvent.SaveEditVariation -> {
                updateProductVariation()
            }

            is UpdateProductEvent.SaveEditImage -> {
                if (state.value.productImageUri != null) {
                    updateProductImage()
                }
            }

            is UpdateProductEvent.Refresh -> {
                getProduct()
            }

            is UpdateProductEvent.ShowError -> {
                _state.value = state.value.copy(showError = event.show)
            }

            is UpdateProductEvent.ShowSuccess -> {
                _state.value = state.value.copy(showSuccess = event.show)
            }

            is UpdateProductEvent.SetEditMode -> {
                _state.value = state.value.copy(editMode = event.editMode)
                if (!event.editMode) updateProduct()
            }

            is UpdateProductEvent.GetProduct -> {
                _state.value = state.value.copy(productId = event.id)
                getProduct()
            }

            is UpdateProductEvent.SelectVariation -> {
                _state.value = state.value.copy(
                    variationName = event.variation.unit,
                    variationPrice = event.variation.price.toString(),
                    variationPriceGrocery = event.variation.priceGrocery.toString(),
                    variationPriceCapital = (event.variation.priceCapital ?: 0).toString(),
                    selectedVariationId = event.variation.id.toString()
                )
            }

            is UpdateProductEvent.SetProductImageUri -> {
                _state.value = state.value.copy(productImageUri = event.uri)
            }

            is UpdateProductEvent.ShowSearchCategory -> {
                _state.value = state.value.copy(showSearchCategory = event.show)
                if (event.show) {
                    getCategories()
                }
            }

            is UpdateProductEvent.SelectCategory -> {
                _state.value = state.value.copy(
                    productCategoryId = event.category.id.toString(),
                    productCategory = event.category.name
                )
            }
        }
    }

    private fun getCategories() {
        getCategoriesUseCase().onEach { res ->
            when (res) {
                is Resource.Loading -> {
                    _state.value = state.value.copy(isLoading = true)
                }

                is Resource.Success -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        categories = res.data ?: emptyList()
                    )
                }

                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        showError = true,
                        error = res.message ?: "An error occurred"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun clearInputs() {

    }

    private fun validateInputs() {

    }

    private fun getProduct() {
        getProductUseCase(state.value.productId).onEach { res ->
            when (res) {
                is Resource.Loading -> {
                    _state.value = state.value.copy(isLoading = true)

                }

                is Resource.Success -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        productName = res.data?.name ?: "",
                        productDescription = res.data?.description ?: "",
                        productImage = res.data?.imageUrl ?: "",
                        productVariations = res.data?.variations ?: emptyList(),
                        productCategoryId = res.data?.categoryId.toString(),
                        productCategory = res.data?.category?.name ?: ""
                    )
                }

                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        showError = true,
                        error = res.message ?: "An error occurred"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun updateProduct() {
        val data = UpdateProductRequest(
            id = state.value.productId.toInt(),
            name = state.value.productName,
            description = state.value.productDescription,
            categoryId = state.value.productCategoryId.toInt(),
        )
        updateProductUseCase(data).onEach { res ->
            when (res) {
                is Resource.Loading -> {
                    _state.value = state.value.copy(isLoading = true)
                }

                is Resource.Success -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        showSuccess = true,
                        success = "Product updated successfully"
                    )
                }

                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        showError = true,
                        error = res.message ?: "An error occurred"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun updateProductImage() {
        val file =
            fileConverter.uriToMultipartBody(uri = state.value.productImageUri, name = "new_image")
        updateImageUseCase(
            productId = state.value.productId,
            image = file
        ).onEach { res ->
            when (res) {
                is Resource.Loading -> {
                    _state.value = state.value.copy(isLoading = true)
                }

                is Resource.Success -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        showSuccess = true,
                        success = "Image updated successfully"
                    )
                }

                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        showError = true,
                        error = res.message ?: "An error occurred"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun updateProductVariation() {
        val data = UpdateProductVariationRequest(
            id = state.value.selectedVariationId.toInt(),
            unit = state.value.variationName,
            price = state.value.variationPrice.toInt(),
            priceGrocery = state.value.variationPriceGrocery.toInt(),
            priceCapital = state.value.variationPriceCapital.toInt()
        )
        updateProductVariationUseCase(data).onEach { res ->
            when (res) {
                is Resource.Loading -> {
                    _state.value = state.value.copy(isLoading = true)
                }

                is Resource.Success -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        showSuccess = true,
                        success = "Variation updated successfully"
                    )
                }

                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        showError = true,
                        error = res.message ?: "An error occurred"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}