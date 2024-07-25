package com.vixiloc.vcashiermobile.presentation.screens.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vixiloc.vcashiermobile.domain.use_case.UseCaseManager
import kotlinx.coroutines.launch

class HomeViewModel(ucm: UseCaseManager) : ViewModel() {
    private val _role = mutableStateOf("")
    val role: State<String> = _role
    val getRoleUseCase = ucm.getRolesUseCase()

    init {
        getRole()
    }

    private fun getRole() {
        viewModelScope.launch {
            getRoleUseCase().collect {
                _role.value = it
            }
        }
    }
}