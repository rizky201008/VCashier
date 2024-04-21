package com.vixiloc.vcashiermobile.presentation.screens.transaction

sealed class TransactionEvent {
    data class ToggleError(val error: String) : TransactionEvent()
}