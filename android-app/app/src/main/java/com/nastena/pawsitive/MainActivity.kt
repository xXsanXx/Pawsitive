package com.nastena.pawsitive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.nastena.pawsitive.repository.datastores.AuthDataStore
import com.nastena.pawsitive.network.RetrofitClient
import com.nastena.pawsitive.network.api.AnimalApi
import com.nastena.pawsitive.network.api.AccountApi
import com.nastena.pawsitive.repository.AnimalRepository
import com.nastena.pawsitive.repository.AccountRepository
import com.nastena.pawsitive.ui.main.MainContent
import com.nastena.pawsitive.ui.theme.PawsitiveTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val authDataStore = AuthDataStore(applicationContext)

        val retrofit = RetrofitClient.create(authDataStore)

        val accountApi = retrofit.create(AccountApi::class.java)
        val animalApi = retrofit.create(AnimalApi::class.java)

        val accountRepository = AccountRepository(accountApi, authDataStore)
        val animalRepository = AnimalRepository(animalApi)

        setContent {
            PawsitiveTheme {
                MainContent(accountRepository, animalRepository)
            }
        }
    }
}

