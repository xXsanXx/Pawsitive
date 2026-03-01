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
import com.nastena.pawsitive.ui.auth.home.HomeScreen
import com.nastena.pawsitive.ui.auth.home.HomeViewModel
import com.nastena.pawsitive.ui.auth.home.HomeViewModelFactory
import com.nastena.pawsitive.ui.auth.login.LoginScreen
import com.nastena.pawsitive.ui.auth.login.LoginViewModel
import com.nastena.pawsitive.ui.auth.login.LoginViewModelFactory
import com.nastena.pawsitive.ui.auth.splash.SplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tokenManager = TokenManager(applicationContext)

        val retrofit = RetrofitClient.create(tokenManager)

        val authApi = retrofit.create(AuthApi::class.java)

        val repository = AuthRepository(authApi)

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
                        onAuthorized = {
                            navController.navigate("home") {
                                popUpTo("splash") { inclusive = true }
                            }
                        },
                        onUnauthorized = {
                            navController.navigate("login") {
                                popUpTo("splash") { inclusive = true }
                            }
                        }
                    )
                }

                composable("login") {
                    LoginScreen(
                        viewModel = loginViewModel,
                        onLoginSuccess = {
                            navController.navigate("home") {
                                popUpTo("login") { inclusive = true}
                            }
                        },
                        onNavigateToRegister = {
                            navController.navigate("register")
                        }
                    )
                }

                composable("register") {
                    RegisterScreen(
                        onRegisterSuccess = {
                            navController.navigate("home") {
                                popUpTo("login") { inclusive = true}
                            }
                        },
                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }

                composable("home") {
                    HomeScreen(
                        viewModel = homeViewModel,
                        onLogout = {
                            navController.navigate("login") {
                                popUpTo("home") { inclusive = true}
                            }
                        }
                    )
                }

            }

        }
    }
}

