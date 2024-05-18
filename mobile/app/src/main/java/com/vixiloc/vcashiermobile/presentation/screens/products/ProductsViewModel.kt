package com.vixiloc.vcashiermobile.presentation.screens.products

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vixiloc.vcashiermobile.commons.FileConverter
import com.vixiloc.vcashiermobile.commons.Resource
import com.vixiloc.vcashiermobile.commons.Strings.TAG
import com.vixiloc.vcashiermobile.domain.model.CreateUpdateProductRequest
import com.vixiloc.vcashiermobile.domain.model.Variation
import com.vixiloc.vcashiermobile.domain.use_case.CreateImage
import com.vixiloc.vcashiermobile.domain.use_case.CreateProduct
import com.vixiloc.vcashiermobile.domain.use_case.GetCategories
import com.vixiloc.vcashiermobile.domain.use_case.GetProduct
import com.vixiloc.vcashiermobile.domain.use_case.GetProducts
import com.vixiloc.vcashiermobile.domain.use_case.UpdateImage
import com.vixiloc.vcashiermobile.domain.use_case.UpdateProduct
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ProductsViewModel(
    private val getProducts: GetProducts,
    private val createImage: CreateImage,
    private val createProduct: CreateProduct,
    private val updateImage: UpdateImage,
    private val fileConverter: FileConverter,
    private val getCategories: GetCategories,
    private val updateProduct: UpdateProduct,
    private val getProduct: GetProduct
) : ViewModel() {
    var state by mutableStateOf(ProductState())

    fun onEvent(e: ProductEvent) {
        when (e) {
            is ProductEvent.DismissAlertMessage -> {
                state = state.copy(
                    success = "",
                    error = ""
                )
            }

            is ProductEvent.InputProductName -> {
                state = state.copy(
                    productName = e.name
                )
            }

            is ProductEvent.InputProductCategory -> {
                state = state.copy(
                    productCategory = e.category
                )
            }

            is ProductEvent.InputProductDescription -> {
                state = state.copy(
                    productDescription = e.description
                )
            }

            is ProductEvent.InputVariationUnit -> {
                state = state.copy(
                    variationUnit = e.unit
                )
            }

            is ProductEvent.InputVariationPrice -> {
                if (e.price.isNotEmpty() && e.price.isDigitsOnly()) {
                    state = state.copy(
                        variationPrice = e.price
                    )
                }
            }

            is ProductEvent.InputVariationPriceGrocery -> {
                if (e.price.isNotEmpty() && e.price.isDigitsOnly()) {
                    state = state.copy(
                        variationPriceGrocery = e.price
                    )
                }
            }

            is ProductEvent.InputVariationStock -> {
                if (e.stock.isNotEmpty() && e.stock.isDigitsOnly()) {
                    state = state.copy(
                        variationStock = e.stock
                    )
                }
            }

            is ProductEvent.InputProductCategoryName -> {
                state = state.copy(
                    categoryName = e.name
                )
            }

            is ProductEvent.InputProductImage -> {
                state = state.copy(
                    image = e.image,
                    alsoUpdateImage = true
                )
            }

            is ProductEvent.SubmitAddVariation -> {
                val variation = Variation(
                    price = state.variationPrice.toInt(),
                    priceGrocery = state.variationPriceGrocery.toInt(),
                    stock = state.variationStock.toInt(),
                    unit = state.variationUnit
                )
                state = state.copy(
                    variations = state.variations.plus(variation),
                    variationUnit = "",
                    variationPrice = "",
                    variationPriceGrocery = "",
                    variationStock = ""
                )
            }

            is ProductEvent.ToggleVariationDialog -> {
                state = state.copy(
                    showVariationDialog = !state.showVariationDialog
                )
            }

            is ProductEvent.SubmitCreateProduct -> {
                processCreateProduct()
            }

            is ProductEvent.SubmitUpdateProduct -> {
                processUpdateProduct()
            }

            is ProductEvent.DeleteVariation -> {
                state = state.copy(
                    variations = state.variations.minus(e.data)
                )
            }

            is ProductEvent.UpdateVariation -> {
                state = state.copy(
                    variationId = e.data.id,
                    variationUnit = e.data.unit,
                    variationPrice = e.data.price.toString(),
                    variationPriceGrocery = e.data.priceGrocery.toString(),
                    variationStock = e.data.stock.toString(),
                    showVariationDialog = true
                )
            }

            is ProductEvent.SubmitUpdateVariation -> {
                onEvent(ProductEvent.ToggleVariationDialog)
                val variation = Variation(
                    id = state.variationId,
                    price = state.variationPrice.toInt(),
                    priceGrocery = state.variationPriceGrocery.toInt(),
                    stock = state.variationStock.toInt(),
                    unit = state.variationUnit
                )
                val oldVariation = state.variations.find { it.id == state.variationId }!!
                state = state.copy(
                    variations = state.variations.minus(oldVariation).plus(variation),
                    variationUnit = "",
                    variationPrice = "",
                    variationPriceGrocery = "",
                    variationStock = "",
                    variationId = 0,
                    showVariationDialog = false
                )
            }
        }
    }

    private fun processCreateProduct() {
        Log.d(TAG, "processCreateProduct: called")
        val data = CreateUpdateProductRequest(
            name = state.productName,
            description = state.productDescription,
            variations = state.variations,
            categoryId = state.productCategory,
            id = null
        )

        createProduct(data = data).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    state = state.copy(isLoading = true)
                }

                is Resource.Success -> {
                    state = state.copy(
                        isLoading = false,
                        productId = resource.data?.id ?: 0,
                        success = "Product created successfully"
                    )
                    if (state.image != null) {
                        onEvent(ProductEvent.DismissAlertMessage)
                        processCreateImage()
                    }
                }

                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        error = resource.message ?: "An unexpected error occurred"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun processCreateImage() {
        val file = fileConverter.uriToMultipartBody(uri = state.image, name = null)

        createImage(
            productId = state.productId.toString(),
            image = file
        ).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    state = state.copy(isLoading = true)
                }

                is Resource.Success -> {
                    state = state.copy(
                        isLoading = false,
                        success = "Product image created successfully"
                    )
                }

                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        error = resource.message ?: "An unexpected error occurred"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun processUpdateProduct() {
        val data = CreateUpdateProductRequest(
            name = state.productName,
            description = state.productDescription,
            variations = state.variations,
            categoryId = state.productCategory,
            id = state.productId
        )

        Log.d(TAG, "processUpdateProduct: $data")

        updateProduct(data = data).onEach { resource ->
            Log.d(TAG, "processUpdateProduct: $data")
            when (resource) {
                is Resource.Loading -> {
                    state = state.copy(isLoading = true)
                }

                is Resource.Success -> {
                    state = state.copy(
                        isLoading = false,
                        success = "Product updated successfully"
                    )
                    if (state.alsoUpdateImage) {
                        onEvent(ProductEvent.DismissAlertMessage)
                        processUpdateImage()
                    }
                }

                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        error = resource.message ?: "An unexpected error occurred"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun processUpdateImage() {
        val file = fileConverter.uriToMultipartBody(uri = state.image, name = "new_image")
        updateImage(productId = state.productId.toString(), image = file).onEach { res ->
            when (res) {
                is Resource.Loading -> {
                    state = state.copy(isLoading = true)
                }

                is Resource.Success -> {
                    state = state.copy(
                        isLoading = false,
                        success = "Product image updated successfully"
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

    fun processGetCategories() {
        getCategories().onEach { res ->
            when (res) {
                is Resource.Loading -> {
                    state = state.copy(isLoading = true)
                }

                is Resource.Success -> {
                    state = state.copy(
                        isLoading = false,
                        categories = res.data?.map {
                            mapOf(it.name to it.id)
                        } ?: emptyList()
                    )
                    Log.d(TAG, "processGetCategories: ${state.categories}")
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

    fun processGetProducts() {
        getProducts().onEach { res ->
            when (res) {
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

    fun processGetProduct(id: String) {
        getProduct(id).onEach { res ->
            when (res) {
                is Resource.Loading -> {
                    state = state.copy(isLoading = true)
                }

                is Resource.Success -> {
                    state = state.copy(
                        isLoading = false,
                        productName = res.data?.name ?: "",
                        productDescription = res.data?.description ?: "",
                        productCategory = res.data?.category?.id ?: 0,
                        categoryName = res.data?.category?.name ?: "",
                        productId = res.data?.id ?: 0,
                        variations = res.data?.variations ?: emptyList(),
                        image = res.data?.imageUrl?.let { Uri.parse(it) },
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