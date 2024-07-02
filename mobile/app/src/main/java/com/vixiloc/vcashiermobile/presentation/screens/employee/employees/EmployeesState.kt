package com.vixiloc.vcashiermobile.presentation.screens.employee.employees

import com.vixiloc.vcashiermobile.domain.model.user.UsersResponseData

data class EmployeesState(
    val isLoading: Boolean = false,
    val error: String = "",
    val success: String = "",
    val users: List<UsersResponseData> = emptyList(),
    val showSuccessAlert: Boolean = false,
    val showErrorAlert: Boolean = false,
    val showAddDialog: Boolean = false,
    val name:String = "",
    val email:String = "",
    val password:String = "",
    val role:String = "cashier"
)