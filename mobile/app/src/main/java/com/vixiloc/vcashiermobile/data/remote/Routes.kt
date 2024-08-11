package com.vixiloc.vcashiermobile.data.remote

import com.vixiloc.vcashiermobile.BuildConfig

object Routes {

    const val BASE_URL = BuildConfig.BASE_URL
    private const val AUTH = "auth"
    const val LOGIN = "$AUTH/login"
    const val LOGOUT = "$AUTH/logout"
    const val REGISTER = "$AUTH/register"
    private const val USER = "users"
    const val GET_ROLE = "$USER/role"
    const val VALIDATE_TOKEN = "$USER/validate"
    const val PRODUCTS = "products"
    const val VARIATIONS = "product-variations"
    const val VARIATION = "$VARIATIONS/{id}"
    const val GET_PRODUCT = "$PRODUCTS/{id}"
    const val CATEGORIES = "categories"
    const val CUSTOMERS = "customers"
    private const val TRANSACTIONS = "transactions"
    const val ALL_TRANSACTIONS = "$TRANSACTIONS/all-transactions"
    const val GET_TRANSACTION = "$TRANSACTIONS/get-transaction/{id}"
    const val CREATE_TRANSACTION = "$TRANSACTIONS/create-transaction"
    const val UPDATE_TRANSACTION = "$TRANSACTIONS/update-transaction"
    const val REPORT_TRANSACTION = "$TRANSACTIONS/reports"
    private const val PRODUCT_IMAGES = "product-images"
    const val ADD_IMAGE = "${PRODUCT_IMAGES}/add"
    const val UPDATE_IMAGE = "${PRODUCT_IMAGES}/update"
    private const val PAYMENT = "payment"
    const val PAYMENT_METHODS = "$PAYMENT/methods"
    const val PAYMENT_MAKE = "$PAYMENT/make-payment"
    const val PAYMENT_CREATE_VA = "$PAYMENT/create-va"
    const val PAYMENT_CHECK_STATUS = "$PAYMENT/check-status/{id}"
    private const val USERS = "users"
    const val USERS_LIST = "$USERS/lists"
    const val RESET_PASSWORD = "$USERS/reset-password/{id}"
    const val UPDATE_USER = "$USERS/update"
    private const val PRODUCT_LOGS = "product-logs"
    const val PRODUCT_LOGS_ALL = "$PRODUCT_LOGS/all"
    const val PRODUCT_LOGS_ADD = "$PRODUCT_LOGS/add"
}