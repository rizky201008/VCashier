package com.vixiloc.vcashiermobile.presentation.navs.routes

import kotlinx.serialization.Serializable

sealed interface MainRoutes {

    @Serializable
    data object SplashScreen : MainRoutes

    @Serializable
    data object LoginScreen : MainRoutes

    @Serializable
    data object NavDrawerScreens : MainRoutes {

        @Serializable
        data object Home : MainRoutes

        @Serializable
        data object Transactions : MainRoutes {

            @Serializable
            data object Checkout : MainRoutes

            @Serializable
            data class PayTransaction(val id: String) : MainRoutes

            @Serializable
            data object SearchCustomer : MainRoutes

            @Serializable
            data class MakePayment(val transactionId: String) : MainRoutes
        }

        @Serializable
        data object Categories : MainRoutes {
            @Serializable
            data object CreateCategory : MainRoutes

            @Serializable
            data class UpdateCategory(
                val name: String,
                val id: Int
            ) : MainRoutes
        }

        @Serializable
        data object Customers : MainRoutes {
            @Serializable
            data object CreateCustomer : MainRoutes

            @Serializable
            data class UpdateCustomer(
                val name: String,
                val phoneNumber: String? = null,
                val id: Int
            ) : MainRoutes
        }

        @Serializable
        data object Products : MainRoutes {
            @Serializable
            data object CreateProduct : MainRoutes

            @Serializable
            data class UpdateProduct(val id: String) : MainRoutes
        }
    }
}