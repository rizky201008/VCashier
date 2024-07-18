package com.vixiloc.vcashiermobile.presentation.navs.hosts.sidebar

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vixiloc.vcashiermobile.domain.use_case.UseCaseManager
import com.vixiloc.vcashiermobile.utils.Resource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SidebarViewModel(useCaseManager: UseCaseManager) : ViewModel() {

    private val _state = mutableStateOf(SidebarState())
    val state: State<SidebarState> = _state
    val logoutUseCase = useCaseManager.logoutUseCase()

    fun onEvent(event: SidebarEvent) {
        when (event) {
            is SidebarEvent.Logout -> {
                logout()
            }

            is SidebarEvent.ShowLogoutDialog -> {
                _state.value = state.value.copy(showLogoutDialog = event.show)
            }

            is SidebarEvent.ShowError -> {
                _state.value = state.value.copy(showError = event.show)
            }

            is SidebarEvent.ShowSuccess -> {
                _state.value = state.value.copy(showSuccess = event.show)
            }

            is SidebarEvent.ChangePageTitle -> {
                _state.value = state.value.copy(pageTitle = event.title)
            }
        }
    }

    private fun logout() {
        logoutUseCase().onEach { res ->
            when (res) {
                is Resource.Loading -> {
                    _state.value = state.value.copy(isLoading = true)
                }

                is Resource.Success -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        showSuccess = true,
                        showLogoutDialog = false,
                        success = res.data?.message ?: "Sukses logout!"
                    )
                }

                is Resource.Error -> {
                    _state.value = state.value.copy(isLoading = false, showError = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}