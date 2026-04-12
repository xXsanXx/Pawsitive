package com.nastena.pawsitive.ui.common

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object Utils {

    private val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    fun dateToAge(dateMillis: Long): Int {
        val birthYear = Instant.ofEpochMilli(dateMillis)
            .atZone(ZoneId.systemDefault()).year
        val currentYear = LocalDate.now().year
        return currentYear - birthYear
    }

    fun formatDate(dateMillis: Long): String {
        val date = Instant.ofEpochMilli(dateMillis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

        return date.format(formatter)
    }
}