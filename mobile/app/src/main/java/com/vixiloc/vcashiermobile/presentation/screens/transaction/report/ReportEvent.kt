package com.vixiloc.vcashiermobile.presentation.screens.transaction.report

sealed class ReportEvent {
    data class ShowError(val show:Boolean): ReportEvent()
}