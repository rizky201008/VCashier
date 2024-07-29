package com.vixiloc.vcashiermobile.domain.model.transactions

data class ReportsResponse(
    val todayOmzet: String,
    val todayProfit: String,
    val transactions: List<TransactionsData>
)
