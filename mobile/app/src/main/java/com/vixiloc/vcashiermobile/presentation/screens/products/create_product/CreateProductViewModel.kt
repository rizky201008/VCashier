package com.vixiloc.vcashiermobile.presentation.screens.products.create_product

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vixiloc.vcashiermobile.domain.model.products.CreateProductRequest
import com.vixiloc.vcashiermobile.domain.model.products.Variation
import com.vixiloc.vcashiermobile.domain.use_case.UseCaseManager
import com.vixiloc.vcashiermobile.utils.FileConverter
import com.vixiloc.vcashiermobile.utils.Resource
import com.vixiloc.vcashiermobile.utils.Strings.TAG
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CreateProductViewModel(
    useCaseManager: UseCaseManager,
    private val fileConverter: FileConverter,
) : ViewModel() {
    val getCategoriesUseCase = useCaseManager.getCategoriesUseCase()
    val createProductUseCase = useCaseManager.createProductUseCase()
    val createImageUseCase = useCaseManager.createImageUseCase()
    val validateNotBlankUseCase = useCaseManager.validateNotBlankUseCase()
    val validateNotEmptyUseCase = useCaseManager.validateNotEmptyUseCase()
    private val _state = mutableStateOf(CreateProductState())
    val state: State<CreateProductState> = _state

    fun onEvent(e: CreateProductEvent) {
        when (e) {
            is CreateProductEvent.ShowAddVariationDialog -> {
                _state.value = state.value.copy(showAddVariation = e.show)
            }

            is CreateProductEvent.ShowErrorMessage -> {
                _state.value = state.value.copy(showError = e.show)
            }

            is CreateProductEvent.ShowSuccessMessage -> {
                _state.value = state.value.copy(showSuccess = e.show)
            }

            is CreateProductEvent.AddVariation -> {
                validateVariationInputs(false)
            }

            is CreateProductEvent.ChangeInput -> {
                when (e.inputName) {
                    InputName.PRODUCT_NAME -> {
                        _state.value = state.value.copy(productName = e.value)
                    }

                    InputName.PRODUCT_DESCRIPTION -> {
                        _state.value = state.value.copy(productDescription = e.value)
                    }

                    InputName.VARIATION_PRICE -> {
                        if (e.value.isDigitsOnly()) {
                            _state.value = state.value.copy(variationPrice = e.value)
                        }
                    }

                    InputName.VARIATION_PRICE_GROCERY -> {
                        if (e.value.isDigitsOnly()) {
                            _state.value = state.value.copy(variationPriceGrocery = e.value)
                        }
                    }

                    InputName.VARIATION_STOCK -> {
                        if (e.value.isDigitsOnly()) {
                            _state.value = state.value.copy(variationStock = e.value)
                        }
                    }

                    InputName.VARIATION_UNIT -> {
                        _state.value = state.value.copy(variationUnit = e.value)
                    }

                    InputName.VARIATION_PRICE_CAPITAL -> {
                        if (e.value.isDigitsOnly()) {
                            _state.value = state.value.copy(variationPriceCapital = e.value)
                        }
                    }
                }
            }

            is CreateProductEvent.SelectCategory -> {
                _state.value = state.value.copy(selectedCategory = e.category)
            }

            is CreateProductEvent.CreateProduct -> {
                validateProductInputs()
            }

            is CreateProductEvent.ChangeImage -> {
                _state.value = _state.value.copy(image = e.image)
            }

            is CreateProductEvent.DeleteVariation -> {
                deleteVariation()
            }

            is CreateProductEvent.UpdateVariation -> {
                validateVariationInputs(true)
            }

            is CreateProductEvent.ShowEditVariationDialog -> {
                _state.value = state.value.copy(showEditVariation = e.show)
                if (!e.show) clearVariationForm()
            }

            is CreateProductEvent.ShowDeleteVariationDialog -> {
                _state.value = state.value.copy(showDeleteVariation = e.show)
                if (!e.show) clearVariationForm()
            }

            is CreateProductEvent.SelectVariation -> {
                _state.value = state.value.copy(selectedVariation = e.data)
                fillVariationForm(e.data)
            }
        }
    }

    private fun fillVariationForm(data: Variation) {
        _state.value = _state.value.copy(
            variationUnit = data.unit,
            variationPrice = data.price.toString(),
            variationPriceGrocery = data.priceGrocery.toString(),
            variationStock = data.stock.toString(),
            variationPriceCapital = data.price.toString()
        )
    }

    private fun updateVariation() {
        val data = state.value.selectedVariation
        val newData = generateVariationModel()
        _state.value = _state.value.copy(
            variations = state.value.variations.minus(data!!).plus(newData),
            selectedVariation = null
        )

        clearVariationForm()
    }

    private fun generateVariationModel(): Variation = Variation(
        price = state.value.variationPrice.toInt(),
        priceGrocery = state.value.variationPriceGrocery.toInt(),
        stock = state.value.variationStock.toInt(),
        unit = state.value.variationUnit,
        priceCapital = state.value.variationPriceCapital.toInt()
    )

    private fun deleteVariation() {
        val variation = state.value.selectedVariation
        _state.value = _state.value.copy(
            variations = state.value.variations.filter { it != variation },
            selectedVariation = null
        )
        clearVariationForm()
    }

    private fun addVariation() {
        val variation = generateVariationModel()
        _state.value = _state.value.copy(
            variations = state.value.variations.plus(variation),
        )
        clearVariationForm()
    }

    private fun clearVariationForm() {
        _state.value = _state.value.copy(
            variationUnit = "",
            variationPrice = "",
            variationPriceGrocery = "",
            variationStock = "",
            variationPriceCapital = ""
        )
    }

    private fun getCategories() {
        getCategoriesUseCase().onEach { res ->
            when (res) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }

                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        categories = res.data ?: emptyList()
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = res.message ?: "An unexpected error occurred"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private suspend fun createProduct(data: CreateProductRequest) {

        createProductUseCase(data = data).collect { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }

                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        productId = resource.data?.id ?: 0,
                        success = "Produk berhasil ditambahkan",
                        showSuccess = true
                    )
                    if (state.value.image != null) {
                        delay(1000)
                        _state.value = _state.value.copy(
                            showSuccess = false
                        )
                    }
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = resource.message ?: "An unexpected error occurred",
                        showError = true
                    )
                }
            }
        }
    }

    private suspend fun createImage() {
        val file = fileConverter.uriToMultipartBody(uri = state.value.image, name = null)
        Log.d(TAG, "createImage: $file")
        createImageUseCase(
            productId = state.value.productId.toString(),
            image = file
        ).collect { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }

                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        success = "Product image created successfully",
                        showSuccess = true
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = resource.message ?: "An unexpected error occurred",
                        showError = true
                    )
                }
            }
        }
    }

    private fun validateVariationInputs(update: Boolean) {
        val variationPrice = state.value.variationPrice
        val variationPriceGrocery = state.value.variationPriceGrocery
        val variationStock = state.value.variationStock
        val variationUnit = state.value.variationUnit
        val variationPriceCapital = state.value.variationPriceCapital

        val variationPriceValidated = validateNotBlankUseCase(variationPrice)
        val variationPriceGroceryValidated = validateNotBlankUseCase(variationPriceGrocery)
        val variationStockValidated = validateNotBlankUseCase(variationStock)
        val variationUnitValidated = validateNotBlankUseCase(variationUnit)
        val variationPriceCapitalValidated = validateNotBlankUseCase(variationPriceCapital)

        val hasError = listOf(
            variationPriceValidated,
            variationPriceGroceryValidated,
            variationStockValidated,
            variationUnitValidated,
            variationPriceCapitalValidated
        ).any { !it.successful }

        if (hasError) {
            _state.value = state.value.copy(
                variationPriceError = variationPriceValidated.errorMessage ?: "",
                variationPriceGroceryError = variationPriceGroceryValidated.errorMessage ?: "",
                variationStockError = variationStockValidated.errorMessage ?: "",
                variationUnitError = variationUnitValidated.errorMessage ?: "",
                variationPriceCapitalError = variationPriceCapitalValidated.errorMessage ?: ""
            )
            return
        }

        if (update) {
            updateVariation()
            return
        }
        addVariation()
    }

    private fun validateProductInputs() {
        val productName = state.value.productName
        val productDescription = state.value.productDescription
        val variations = state.value.variations

        val productNameValidated = validateNotBlankUseCase(productName)
        val productDescriptionValidated = validateNotBlankUseCase(productDescription)
        val variationsValidated = validateNotEmptyUseCase(variations)

        val hasError = listOf(
            productNameValidated,
            productDescriptionValidated,
            variationsValidated
        ).any { !it.successful }

        if (hasError) {
            _state.value = state.value.copy(
                productNameError = productNameValidated.errorMessage ?: "",
                productDescriptionError = productDescriptionValidated.errorMessage ?: "",
                error = if (variationsValidated.errorMessage != null) "Wajib menambahkan minimal 1 variasi" else "",
                showError = variationsValidated.errorMessage != null
            )
            return
        }

        val data = CreateProductRequest(
            name = state.value.productName,
            description = state.value.productDescription,
            variations = state.value.variations,
            categoryId = state.value.selectedCategory?.id ?: 0,
            id = null
        )

        viewModelScope.launch {
            createProduct(data)
            createImage()
        }
    }

    init {
        getCategories()
    }
}