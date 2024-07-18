package com.vixiloc.vcashiermobile.di

import android.content.Context
import com.vixiloc.vcashiermobile.utils.FileConverter
import com.vixiloc.vcashiermobile.domain.use_case.UseCaseManager
import com.vixiloc.vcashiermobile.presentation.navs.hosts.sidebar.SidebarViewModel
import com.vixiloc.vcashiermobile.presentation.screens.category.CategoryViewModel
import com.vixiloc.vcashiermobile.presentation.screens.customer.CustomerViewModel
import com.vixiloc.vcashiermobile.presentation.screens.employee.EmployeesViewModel
import com.vixiloc.vcashiermobile.presentation.screens.login.LoginViewModel
import com.vixiloc.vcashiermobile.presentation.screens.product_log.add_logs.AddProductLogViewModel
import com.vixiloc.vcashiermobile.presentation.screens.product_log.list_logs.ProductLogViewModel
import com.vixiloc.vcashiermobile.presentation.screens.products.list_products.ProductsViewModel
import com.vixiloc.vcashiermobile.presentation.screens.products.create_product.CreateProductViewModel
import com.vixiloc.vcashiermobile.presentation.screens.transaction.transactions.TransactionViewModel
import com.vixiloc.vcashiermobile.presentation.screens.transaction.checkout.CheckoutScreenViewModel
import com.vixiloc.vcashiermobile.presentation.screens.transaction.create_transaction.CreateTransactionViewModel
import com.vixiloc.vcashiermobile.presentation.screens.transaction.customer.SearchCustomerViewModel
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
    viewModel { provideEmployeesViewModel(get()) }
    viewModel { provideSearchCustomerViewModel(get()) }
    viewModel {
        provideCreateProductViewModel(get(), get())
    }
    viewModel {
        provideProductLogViewModel(get())
    }
    viewModel { provideAddProductLogViewModel(get()) }
    viewModel { provideSidebarViewModel(get()) }
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

fun provideEmployeesViewModel(useCaseManager: UseCaseManager) =
    EmployeesViewModel(useCaseManager = useCaseManager)

fun provideSearchCustomerViewModel(useCaseManager: UseCaseManager) =
    SearchCustomerViewModel(useCaseManager = useCaseManager)

fun provideCreateProductViewModel(useCaseManager: UseCaseManager, context: Context) =
    CreateProductViewModel(
        useCaseManager = useCaseManager,
        fileConverter = FileConverter(context = context)
    )

fun provideProductLogViewModel(useCaseManager: UseCaseManager) =
    ProductLogViewModel(useCaseManager = useCaseManager)

fun provideAddProductLogViewModel(useCaseManager: UseCaseManager) =
    AddProductLogViewModel(useCaseManager = useCaseManager)

fun provideSidebarViewModel(useCaseManager: UseCaseManager) =
    SidebarViewModel(useCaseManager = useCaseManager)