package com.vixiloc.vcashiermobile.data.remote

object Routes {

    const val BASE_URL = "https://vc.vixiloc.com/api/"
    private const val AUTH = "auth"
    const val LOGIN = "$AUTH/login"
    const val REGISTER = "$AUTH/register"
    const val PRODUCTS = "products"
    const val CATEGORIES = "categories"
    const val CUSTOMERS = "customers"
    const val TRANSACTIONS = "transactions"
    private const val PRODUCT_IMAGES = "product-images"
    const val ADD_IMAGE = "${PRODUCT_IMAGES}/add"
    const val UPDATE_IMAGE = "${PRODUCT_IMAGES}/update"
    private const val PAYMENT = "payment"
    const val PAYMENT_METHODS = "$PAYMENT/methods"
    const val PAYMENT_MAKE = "$PAYMENT/make-payment"
    const val PAYMENT_CREATE_VA = "$PAYMENT/create-va"
    private const val USERS = "users"
    const val USERS_LIST = "$USERS/lists"
    const val RESET_PASSWORD = "$USERS/reset-password/{id}"
    private const val PRODUCT_LOGS = "product-logs"
    const val PRODUCT_LOGS_ALL = "$PRODUCT_LOGS/all"
    const val PRODUCT_LOGS_ADD = "$PRODUCT_LOGS/add"
}