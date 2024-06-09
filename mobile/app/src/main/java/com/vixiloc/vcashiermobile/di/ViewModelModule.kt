package com.vixiloc.vcashiermobile.di

import android.content.Context
import com.vixiloc.vcashiermobile.commons.FileConverter
import com.vixiloc.vcashiermobile.domain.use_case.CreateCategory
import com.vixiloc.vcashiermobile.domain.use_case.CreateCustomer
import com.vixiloc.vcashiermobile.domain.use_case.CreateImage
import com.vixiloc.vcashiermobile.domain.use_case.CreateProduct
import com.vixiloc.vcashiermobile.domain.use_case.CreateTransaction
import com.vixiloc.vcashiermobile.domain.use_case.DeleteCategory
import com.vixiloc.vcashiermobile.domain.use_case.DeleteCustomer
import com.vixiloc.vcashiermobile.domain.use_case.GetCategories
import com.vixiloc.vcashiermobile.domain.use_case.GetCustomers
import com.vixiloc.vcashiermobile.domain.use_case.GetProduct
import com.vixiloc.vcashiermobile.domain.use_case.GetProducts
import com.vixiloc.vcashiermobile.domain.use_case.GetToken
import com.vixiloc.vcashiermobile.domain.use_case.Login
import com.vixiloc.vcashiermobile.domain.use_case.UpdateCategory
import com.vixiloc.vcashiermobile.domain.use_case.UpdateCustomer
import com.vixiloc.vcashiermobile.domain.use_case.UpdateImage
import com.vixiloc.vcashiermobile.domain.use_case.UpdateProduct
import com.vixiloc.vcashiermobile.domain.use_case.UseCaseManager
import com.vixiloc.vcashiermobile.presentation.screens.login.LoginViewModel
import com.vixiloc.vcashiermobile.presentation.screens.transaction.TransactionViewModel
import com.vixiloc.vcashiermobile.presentation.screens.welcome.WelcomeViewModel
import com.vixiloc.vcashiermobile.presentation.screens.category.CategoryViewModel
import com.vixiloc.vcashiermobile.presentation.screens.customer.CustomerViewModel
import com.vixiloc.vcashiermobile.presentation.screens.products.ProductsViewModel
import com.vixiloc.vcashiermobile.presentation.screens.transaction.transaction_payment.TransactionPaymentViewModel
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