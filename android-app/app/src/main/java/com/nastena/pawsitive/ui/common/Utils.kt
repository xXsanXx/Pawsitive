package com.nastena.pawsitive.ui.common

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

object Utils {

    fun dateToAge(dateMillis: Long): Int {
        val birthYear = Instant.ofEpochMilli(dateMillis)
            .atZone(ZoneId.systemDefault()).year
        val currentYear = LocalDate.now().year
        return currentYear - birthYear
    }
}