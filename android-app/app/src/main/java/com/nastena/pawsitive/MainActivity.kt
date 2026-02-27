package com.nastena.pawsitive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.nastena.pawsitive.data.datastore.TokenManager
import com.nastena.pawsitive.data.remote.RetrofitClient
import com.nastena.pawsitive.data.remote.api.AuthApi
import com.nastena.pawsitive.data.repository.AuthRepository
import com.nastena.pawsitive.ui.auth.LoginScreen
import com.nastena.pawsitive.ui.auth.LoginViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tokenManager = TokenManager(applicationContext)

        val retrofit = RetrofitClient.create(tokenManager)

        val authApi = retrofit.create(AuthApi::class.java)

        val repository = AuthRepository(authApi)

        setContent {
            LoginScreen(
                viewModel = LoginViewModel(
                    repository = repository,
                    tokenManager = tokenManager
                )
            )
        }
    }
}

