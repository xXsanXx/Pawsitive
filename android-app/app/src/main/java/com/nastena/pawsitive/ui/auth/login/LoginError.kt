package com.nastena.pawsitive.ui.auth.login

import com.nastena.pawsitive.ui.auth.register.RegisterError

sealed class LoginError {
    object EmptyFields : LoginError()
    object InvalidEmail : LoginError()
    object WeakPassword : LoginError()
    object InvalidCredentials : LoginError()
    object NetworkError : LoginError()

    data class ServerError(val message: String) : LoginError()

    object Unknown : LoginError()
}