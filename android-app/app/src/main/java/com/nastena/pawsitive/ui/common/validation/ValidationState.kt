package com.nastena.pawsitive.ui.common.validation

sealed interface ValidationState {
    object Valid: ValidationState
    object InvalidFormat: ValidationState
    object Empty: ValidationState
}