package com.vixiloc.vcashiermobile.data.remote

object Routes {

    const val BASE_URL = "https://vc.vixiloc.com/api/"
    const val LOGIN = "auth/login"
    const val REGISTER = "auth/register"
    const val PRODUCTS = "products"
    const val CATEGORIES = "categories"
    const val CUSTOMERS = "customers"
    const val TRANSACTIONS = "transactions"
    private const val PRODUCT_IMAGES= "product-images"
    const val ADD_IMAGE = "${PRODUCT_IMAGES}/add"
    const val UPDATE_IMAGE = "${PRODUCT_IMAGES}/update"
    const val PAYMENT = "payment"
    const val PAYMENT_METHODS = "$PAYMENT/methods"
    const val PAYMENT_MAKE = "$PAYMENT/make-payment"
}