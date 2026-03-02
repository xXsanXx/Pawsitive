package com.nastena.pawsitive.ui.auth.register


sealed class RegisterError {
    object EmptyFields : RegisterError()
    object InvalidEmail : RegisterError()
    object WeakPassword : RegisterError()
    object InvalidRole : RegisterError()
    object EmailAlreadyExists : RegisterError()

    data class ServerError(val message: String) : RegisterError()

    object Unknown : RegisterError()
}