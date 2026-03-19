package com.nastena.pawsitive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.nastena.pawsitive.repository.datastores.AuthDataStore
import com.nastena.pawsitive.network.RetrofitClient
import com.nastena.pawsitive.network.api.AccountApi
import com.nastena.pawsitive.network.api.UserApi
import com.nastena.pawsitive.repository.AccountRepository
import com.nastena.pawsitive.repository.UserRepository
import com.nastena.pawsitive.ui.main.MainContent
import com.nastena.pawsitive.ui.theme.PawsitiveTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val authDataStore = AuthDataStore(applicationContext)

        val retrofit = RetrofitClient.create(authDataStore)

        val accountApi = retrofit.create(AccountApi::class.java)

        val accountRepository = AccountRepository(accountApi, authDataStore)

        val userApi = retrofit.create(UserApi::class.java)

        val userRepository = UserRepository(userApi)

        setContent {
            PawsitiveTheme {
                MainContent(accountRepository = accountRepository, userRepository = userRepository)
            }
        }
    }
}

