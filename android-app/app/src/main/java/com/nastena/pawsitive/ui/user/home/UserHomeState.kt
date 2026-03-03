package com.nastena.pawsitive.ui.user.home

import android.os.Message
import com.nastena.pawsitive.ui.model.AnimalUi

sealed class UserHomeState {
    object Loading : UserHomeState()
    data class Success(val animals: List<AnimalUi>) : UserHomeState()
    data class Error(val message: String) : UserHomeState()
}