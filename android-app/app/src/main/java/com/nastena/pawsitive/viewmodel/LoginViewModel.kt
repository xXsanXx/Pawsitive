package com.nastena.pawsitive.viewmodel

import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel(){
    var email: String = ""
    var password: String = ""

    fun login() {
        println("Login with $email and $password")
    }
}
