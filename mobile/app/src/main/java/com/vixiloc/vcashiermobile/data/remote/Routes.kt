package com.vixiloc.vcashiermobile.data.remote

object Routes {

    const val BASE_URL = "http://10.0.2.2:8000/api/"
    const val LOGIN = "auth/login"
    const val PRODUCTS = "products"
    const val CATEGORIES = "categories"
    const val CUSTOMERS = "customers"
    const val TRANSACTIONS = "transactions"
    private const val PRODUCT_IMAGES= "product-images"
    const val ADD_IMAGE = "${PRODUCT_IMAGES}/add"
    const val UPDATE_IMAGE = "${PRODUCT_IMAGES}/update"
}