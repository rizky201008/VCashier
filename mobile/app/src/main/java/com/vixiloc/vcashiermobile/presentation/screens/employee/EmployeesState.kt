package com.vixiloc.vcashiermobile.presentation.screens.employee

import com.vixiloc.vcashiermobile.domain.model.user.UsersResponseData

data class EmployeesState(
    val isLoading: Boolean = false,
    val error: String = "",
    val success: String = "",
    val users: List<UsersResponseData> = emptyList(),
    val showSuccessAlert: Boolean = false,
    val showErrorAlert: Boolean = false,
    val showAddDialog: Boolean = false,
    val showResetPasswordAlert: Boolean = false,
    val name: String = "",
    val nameError: String = "",
    val email: String = "",
    val emailError: String = "",
    val password: String = "",
    val passwordError: String = "",
    val passwordConfirmation: String = "",
    val passwordConfirmationError: String = "",
    val passwordHidden: Boolean = true,
    val role: String = "cashier",
    val selectedEmployee: Int? = null
)