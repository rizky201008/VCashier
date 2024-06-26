package com.vixiloc.vcashiermobile.di

import android.content.Context
import com.vixiloc.vcashiermobile.commons.FileConverter
import com.vixiloc.vcashiermobile.domain.use_case.UseCaseManager
import com.vixiloc.vcashiermobile.presentation.screens.category.CategoryViewModel
import com.vixiloc.vcashiermobile.presentation.screens.customer.CustomerViewModel
import com.vixiloc.vcashiermobile.presentation.screens.login.LoginViewModel
import com.vixiloc.vcashiermobile.presentation.screens.products.ProductsViewModel
import com.vixiloc.vcashiermobile.presentation.screens.transaction.TransactionViewModel
import com.vixiloc.vcashiermobile.presentation.screens.transaction.checkout.CheckoutScreenViewModel
import com.vixiloc.vcashiermobile.presentation.screens.transaction.create_transaction.CreateTransactionViewModel
import com.vixiloc.vcashiermobile.presentation.screens.transaction.pay_transaction.PayTransactionViewModel
import com.vixiloc.vcashiermobile.presentation.screens.transaction.transaction_payment.TransactionPaymentViewModel
import com.vixiloc.vcashiermobile.presentation.screens.welcome.WelcomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { provideLoginViewModel(get()) }
    viewModel { provideWelcomeViewModel(get()) }
    viewModel { provideTransactionViewModel(get()) }
    viewModel { provideCategoryViewModel(get()) }
    viewModel { provideCustomerViewModel(get()) }
    viewModel { provideProductViewModel(androidContext(), get()) }
    viewModel { provideTransactionPaymentViewModel(get()) }
    viewModel { providePayTransactionViewModel(get()) }
    viewModel { provideCreateTransactionViewModel(get()) }
    viewModel { provideCheckoutScreenViewModel(get()) }
}

fun provideLoginViewModel(useCaseManager: UseCaseManager): LoginViewModel {
    return LoginViewModel(useCaseManager = useCaseManager)
}

fun provideWelcomeViewModel(useCaseManager: UseCaseManager): WelcomeViewModel {
    return WelcomeViewModel(useCaseManager = useCaseManager)
}

fun provideTransactionViewModel(
    useCaseManager: UseCaseManager
): TransactionViewModel {
    return TransactionViewModel(
        useCaseManager = useCaseManager
    )
}

fun provideCategoryViewModel(
    useCaseManager: UseCaseManager
): CategoryViewModel {
    return CategoryViewModel(
        useCaseManager = useCaseManager
    )
}

fun provideCustomerViewModel(
    useCaseManager: UseCaseManager
): CustomerViewModel {
    return CustomerViewModel(
        useCaseManager = useCaseManager
    )
}

fun provideProductViewModel(
    context: Context,
    useCaseManager: UseCaseManager,
): ProductsViewModel {
    return ProductsViewModel(
        fileConverter = FileConverter(context = context),
        useCaseManager = useCaseManager
    )
}

fun provideTransactionPaymentViewModel(useCaseManager: UseCaseManager) =
    TransactionPaymentViewModel(useCaseManager = useCaseManager)

fun providePayTransactionViewModel(useCaseManager: UseCaseManager) =
    PayTransactionViewModel(useCaseManager = useCaseManager)

fun provideCreateTransactionViewModel(useCaseManager: UseCaseManager) =
    CreateTransactionViewModel(useCaseManager = useCaseManager)

fun provideCheckoutScreenViewModel(useCaseManager: UseCaseManager) =
    CheckoutScreenViewModel(useCaseManager = useCaseManager)