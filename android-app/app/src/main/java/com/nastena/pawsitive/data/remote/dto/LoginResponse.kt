package com.nastena.pawsitive.data.remote.dto

data class LoginResponse(
    val token: String,
    val role: Role
)
