package com.vixiloc.vcashiermobile.presentation.screens.employee

sealed class EmployeesEvent {
    data class ShowSuccessAlert(val show: Boolean) : EmployeesEvent()
    data class ShowErrorAlert(val show: Boolean) : EmployeesEvent()
    data class ShowAddDialog(val show: Boolean) : EmployeesEvent()
    data class ChangeInput(val name: InputName, val value: String) : EmployeesEvent()
    data object AddEmployee : EmployeesEvent()
    data class ShowResetPasswordAlert(val show: Boolean) : EmployeesEvent()
    data object ResetPassword : EmployeesEvent()
    data class SelectEmployee(val id: Int?) : EmployeesEvent()
    data class TogglePasswordVisibility(val show: Boolean) : EmployeesEvent()
    class ShowEditDialog(val show: Boolean) : EmployeesEvent()
    object UpdateEmployee : EmployeesEvent()
}

enum class InputName {
    NAME,
    EMAIL,
    PASSWORD,
    PASSWORD_CONFIRMATION,
    ROLE
}