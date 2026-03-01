package com.nastena.pawsitive.data.remote.dto

data class RegisterRequest(
    val email: String,
    val password: String,
    val role: String
)
