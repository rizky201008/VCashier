package com.vixiloc.vcashiermobile.presentation.screens.employee.employees

sealed class EmployeesEvent {
    data class ShowSuccessAlert(val show: Boolean) : EmployeesEvent()
    data class ShowErrorAlert(val show: Boolean) : EmployeesEvent()
    data class ShowAddDialog(val show: Boolean) : EmployeesEvent()
    data class ChangeInput(val name: InputName, val value: String) : EmployeesEvent()
    data object AddEmployee : EmployeesEvent()
    data class ShowResetPasswordAlert(val show: Boolean) : EmployeesEvent()
    data class ShowDeleteEmployeeAlert(val show: Boolean) : EmployeesEvent()
    data object ResetPassword : EmployeesEvent()
    data object DeleteEmployee : EmployeesEvent()
    data class SelectEmployee(val id: Int?) : EmployeesEvent()
}

enum class InputName {
    NAME,
    EMAIL,
    PASSWORD,
    ROLE
}