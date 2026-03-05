package com.nastena.pawsitive.legacy_data.remote.dto

import com.nastena.pawsitive.dto.AccountRole

data class RegisterRequest(
    val email: String,
    val password: String,
    val role: AccountRole
)
