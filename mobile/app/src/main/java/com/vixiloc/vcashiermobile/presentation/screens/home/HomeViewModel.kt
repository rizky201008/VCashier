package com.vixiloc.vcashiermobile.presentation.screens.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vixiloc.vcashiermobile.domain.model.screen.getListHomeMenus
import com.vixiloc.vcashiermobile.domain.use_case.UseCaseManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(ucm: UseCaseManager) : ViewModel() {
    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state
    val getRoleUseCase = ucm.getRolesUseCase()

    init {
        viewModelScope.launch {
            getRole()
            delay(1000)
            getHomeMenus()
        }
    }

    private fun getHomeMenus() {
        val menus = getListHomeMenus(state.value.role)
        _state.value = state.value.copy(listHomeMenu = menus)
    }

    private suspend fun getRole() {
        viewModelScope.launch {
            getRoleUseCase().collect {
                _state.value = state.value.copy(role = it)
            }
        }
    }
}