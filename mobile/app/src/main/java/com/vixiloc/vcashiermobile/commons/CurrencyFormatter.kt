package com.vixiloc.vcashiermobile.commons

import java.text.NumberFormat
import java.util.Currency

object CurrencyFormatter {
    fun formatCurrency(amount: Int): String {
        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        format.setMaximumFractionDigits(0)
        format.setCurrency(Currency.getInstance("IDR"))

        return format.format(amount)
    }
}