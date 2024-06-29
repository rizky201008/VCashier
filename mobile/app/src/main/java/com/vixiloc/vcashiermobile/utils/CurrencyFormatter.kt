package com.vixiloc.vcashiermobile.utils

import java.text.NumberFormat
import java.util.Currency

object CurrencyFormatter {
    fun formatCurrency(amount: Int): String {
        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        format.setMaximumFractionDigits(0)
        format.currency = Currency.getInstance("IDR")

        val result = format.format(amount)
        return result.replace("IDR", "Rp ")
    }
}