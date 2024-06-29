package com.vixiloc.vcashiermobile.utils

import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object DateFormatter {

    fun format(date: String): String {

        // Parser untuk format timestamp
        val formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME

        // Parsing timestamp ke ZonedDateTime
        val utcDateTime = ZonedDateTime.parse(date, formatter)

        // Konversi ke zona waktu lokal
        val localDateTime = utcDateTime.withZoneSameInstant(ZoneId.systemDefault())

        // Format hasil ke format yang diinginkan
        val outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
        val formattedDateTime = localDateTime.format(outputFormatter)
        return formattedDateTime
    }
}