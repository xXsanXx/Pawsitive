package com.nastena.pawsitive.ui.screens.user.profile

data class UserProfileState(
    val email: String = "",

    val name: String = "",
    val description: String = "",

    val isEditing: Boolean = false
)
