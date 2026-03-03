package com.nastena.pawsitive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nastena.pawsitive.data.datastore.TokenManager
import com.nastena.pawsitive.data.remote.RetrofitClient
import com.nastena.pawsitive.data.remote.api.AuthApi
import com.nastena.pawsitive.data.repository.AuthRepository
import com.nastena.pawsitive.ui.home.HomeViewModel
import com.nastena.pawsitive.ui.home.HomeViewModelFactory
import com.nastena.pawsitive.ui.auth.login.LoginScreen
import com.nastena.pawsitive.ui.auth.login.LoginViewModel
import com.nastena.pawsitive.ui.auth.login.LoginViewModelFactory
import com.nastena.pawsitive.ui.auth.register.RegisterScreen
import com.nastena.pawsitive.ui.auth.register.RegisterViewModel
import com.nastena.pawsitive.ui.auth.register.RegisterViewModelFactory
import com.nastena.pawsitive.ui.auth.splash.SplashScreen
import com.nastena.pawsitive.ui.home.ShelterHomeScreen
import com.nastena.pawsitive.ui.home.UserHomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tokenManager = TokenManager(applicationContext)

        val retrofit = RetrofitClient.create(tokenManager)

        val authApi = retrofit.create(AuthApi::class.java)

        val repository = AuthRepository(authApi, tokenManager)

        setContent {

            val navController = rememberNavController()

            val loginViewModel: LoginViewModel = viewModel(
                factory = LoginViewModelFactory(repository, tokenManager)
            )

            val homeViewModel: HomeViewModel = viewModel(
                factory = HomeViewModelFactory(tokenManager)
            )

            NavHost(
                navController = navController,
                startDestination = "splash"
            ) {
                composable("splash") {
                    SplashScreen(
                        tokenManager = tokenManager,
                        navController = navController
                    )
                }

                composable("login") {
                    LoginScreen(
                        viewModel = loginViewModel,
                        navController = navController,
                        tokenManager = tokenManager,
                        onNavigateToRegister = {
                            navController.navigate("register")
                        }
                    )
                }

                composable("register") {
                    val registerViewModel: RegisterViewModel = viewModel(
                        factory = RegisterViewModelFactory(repository)
                    )
                    RegisterScreen(
                        viewModel = registerViewModel,
                        onRegisterSuccess = {
                            navController.navigate("login") {
                                popUpTo("register") { inclusive = true }
                            }
                        },
                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }

                composable("user_home") {
                    UserHomeScreen(
                        onLogout = {
                            navController.navigate("login") {
                                popUpTo("user_home") { inclusive = true}
                            }
                        }
                    )
                }

                composable("shelter_user") {
                    ShelterHomeScreen(
                        onLogout = {
                            navController.navigate("login") {
                                popUpTo("_home") { inclusive = true}
                            }
                        }
                    )
                }

            }

        }
    }
}

