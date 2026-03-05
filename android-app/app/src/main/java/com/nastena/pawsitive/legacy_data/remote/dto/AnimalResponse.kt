package com.nastena.pawsitive.legacy_data.remote.dto

data class AnimalResponse(
    val id: Long,
    val name: String,
    val type: String,
    val breed: String,
    val age: Int,
    val gender: String,
    val healthInfo: String,
    val shelterName: String

)