package com.vixiloc.vcashiermobile.presentation.screens.transaction.report

import com.vixiloc.vcashiermobile.domain.model.transactions.ReportsResponse

data class ReportState(
    val isLoading: Boolean = false,
    val data: ReportsResponse? = null,
    val error: String = "",
    val showError: Boolean = false,
)
