package com.nastena.pawsitive.ui.common

import com.nastena.pawsitive.dto.AdoptionStatus

fun AdoptionStatus.isFinal(): Boolean {
    return when (this) {
        AdoptionStatus.NONE,
        AdoptionStatus.PENDING -> false

        AdoptionStatus.CANCELED,
        AdoptionStatus.APPROVED,
        AdoptionStatus.REJECTED -> true
    }
}