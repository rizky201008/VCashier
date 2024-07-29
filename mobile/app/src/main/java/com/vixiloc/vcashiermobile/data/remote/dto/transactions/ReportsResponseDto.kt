package com.vixiloc.vcashiermobile.data.remote.dto.transactions

import com.google.gson.annotations.SerializedName
import com.vixiloc.vcashiermobile.domain.model.transactions.ReportsResponse

data class ReportsResponseDto(
    @SerializedName("today_omzet")
    val todayOmzet: String,
    @SerializedName("today_profit")
    val todayProfit: String,
    @SerializedName("latest_transaction")
    val transactions: List<TransactionsDataDto>
)

fun ReportsResponseDto.toDomain() = ReportsResponse(
    todayOmzet = todayOmzet,
    todayProfit = todayProfit,
    transactions = transactions.map { it.toDomain() }
)