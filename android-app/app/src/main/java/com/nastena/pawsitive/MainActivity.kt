package com.nastena.pawsitive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.nastena.pawsitive.network.OkHttpClient
import com.nastena.pawsitive.network.RetrofitClient
import com.nastena.pawsitive.network.api.AccountApi
import com.nastena.pawsitive.network.api.AdoptionApi
import com.nastena.pawsitive.network.api.AnimalApi
import com.nastena.pawsitive.network.api.FavoriteApi
import com.nastena.pawsitive.network.api.ShelterApi
import com.nastena.pawsitive.network.api.UserApi
import com.nastena.pawsitive.repository.AccountRepository
import com.nastena.pawsitive.repository.FilesRepository
import com.nastena.pawsitive.repository.ShelterRepository
import com.nastena.pawsitive.repository.UserRepository
import com.nastena.pawsitive.repository.datastores.AuthDataStore
import com.nastena.pawsitive.ui.main.MainContent
import com.nastena.pawsitive.ui.theme.PawsitiveTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val authDataStore = AuthDataStore(applicationContext)
        val okHttpClient = OkHttpClient.get(authDataStore)
        val retrofit = RetrofitClient.create(okHttpClient)

        val accountApi = retrofit.create(AccountApi::class.java)
        val userApi = retrofit.create(UserApi::class.java)
        val shelterApi = retrofit.create(ShelterApi::class.java)
        val animalApi = retrofit.create(AnimalApi::class.java)
        val favoriteApi = retrofit.create(FavoriteApi::class.java)
        val adoptionApi = retrofit.create(AdoptionApi::class.java)

        val accountRepository = AccountRepository(accountApi, authDataStore)
        val userRepository =
            UserRepository(userApi, shelterApi, animalApi, favoriteApi, adoptionApi)
        val shelterRepository = ShelterRepository(
            shelterApi,
            _animalsApi = animalApi,
            _contentResolver = contentResolver
        )
        val filesRepository = FilesRepository()

        setContent {
            PawsitiveTheme {
                MainContent(
                    accountRepository = accountRepository,
                    userRepository = userRepository,
                    shelterRepository = shelterRepository,
                    filesRepository = filesRepository,
                )
            }
        }
    }
}

