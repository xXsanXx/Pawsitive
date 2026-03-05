package com.nastena.pawsitive.ui.main

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nastena.pawsitive.R
import com.nastena.pawsitive.dto.AccountRole
import com.nastena.pawsitive.repository.AccountRepository
import com.nastena.pawsitive.repository.AnimalRepository
import com.nastena.pawsitive.ui.screens.BaseScreenViewModel
import com.nastena.pawsitive.ui.screens.login.LoginView
import com.nastena.pawsitive.ui.screens.login.LoginViewModel
import com.nastena.pawsitive.ui.screens.login.LoginViewModelFactory
import com.nastena.pawsitive.ui.screens.splash.SplashView
import com.nastena.pawsitive.ui.screens.splash.SplashViewModel
import com.nastena.pawsitive.ui.screens.splash.SplashViewModelFactory

object NavigationRoutes {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val REGISTER = "register"

    const val USER_HOME = "user_home"

    const val SHELTER_HOME = "shelter_home"

    fun fromAccountRole(role: AccountRole) = when (role) {
        AccountRole.USER -> USER_HOME
        AccountRole.SHELTER -> SHELTER_HOME
    }
}

@Composable
fun MainContent(
    accountRepository: AccountRepository,
    animalRepository: AnimalRepository
) {
    val mainViewModel: MainViewModel = viewModel()

    val splashViewModel: SplashViewModel = viewModel(
        factory = SplashViewModelFactory(accountRepository, mainViewModel)
    )

    val loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(mainViewModel, accountRepository)
    )

//    val registerViewModel: RegisterViewModel = viewModel(
//        factory = RegisterViewModelFactory(accountRepository)
//    )
//
//    val userHomeViewModel: UserHomeViewModel = viewModel(
//        factory = UserHomeViewModelFactory(animalRepository)
//    )

    val navController: NavHostController = rememberNavController()

    Navigation(
        navController = navController,
        splashViewModel = splashViewModel,
//        registerViewModel = registerViewModel,
        loginViewModel = loginViewModel
//        userHomeViewModel = userHomeViewModel
    )

    LaunchedEffect(Unit) {
        mainViewModel.navigationEvents.collect { event: MainUiEvents.Navigation ->
            when (event) {
                is MainUiEvents.Navigation.To -> {
                    navController.navigate(event.route) {
                        when (val popUpType = event.popUpType) {
                            MainUiEvents.Navigation.To.PopUpType.None -> {}
                            MainUiEvents.Navigation.To.PopUpType.Origin -> {
                                popUpTo(0)
                            }
                            is MainUiEvents.Navigation.To.PopUpType.Route -> {
                                popUpTo(popUpType.route) { inclusive = true }
                            }
                        }
                    }
                }

                MainUiEvents.Navigation.Back -> {
                    navController.popBackStack()
                }
            }
        }
    }

    val screenState by mainViewModel.mainState.collectAsState()

    AnimatedVisibility(
        visible = screenState is MainState.Loading
    ) {
        Loading()
    }

    AnimatedVisibility(
        visible = screenState is MainState.Error
    ) {
        if (screenState is MainState.Error) {
            val errorState = screenState as MainState.Error
            ErrorBox(throwable = errorState.throwable, onEvent = { event ->
                mainViewModel.onViewEvent(event)
            })
        }
    }
}

@Composable
private fun Navigation(
    navController: NavHostController,
    splashViewModel: SplashViewModel,
//    registerViewModel: RegisterViewModel,
    loginViewModel: LoginViewModel
//    userHomeViewModel: UserHomeViewModel
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.SPLASH
    ) {
        composable(NavigationRoutes.SPLASH) {
            ScreenView(splashViewModel) {
                SplashView(viewModel = splashViewModel)
            }
        }

        composable(NavigationRoutes.REGISTER) {
            Text("Register screen")

//            RegisterScreen(
//                viewModel = registerViewModel,
//                onRegisterSuccess = {
//                    navController.navigate(NavigationRoutes.LOGIN) {
//                        popUpTo(NavigationRoutes.REGISTER) { inclusive = true }
//                    }
//                },
//                onBack = {
//                    navController.popBackStack()
//                }
//            )
        }

        composable(NavigationRoutes.LOGIN) {
            ScreenView(loginViewModel) {
                LoginView(viewModel = loginViewModel)
            }
        }

        composable(NavigationRoutes.USER_HOME) {
            Text("User home screen")

//            UserHomeScreen(
//                viewModel = userHomeViewModel,
//                onLogout = {
//                    navController.navigate(NavigationRoutes.LOGIN) {
//                        popUpTo(NavigationRoutes.USER_HOME) { inclusive = true }
//                    }
//                }
//            )
        }

        composable(NavigationRoutes.SHELTER_HOME) {
            Text("Shelter home screen")

//            ShelterHomeScreen(
//                onLogout = {
//                    navController.navigate(NavigationRoutes.LOGIN) {
//                        popUpTo(NavigationRoutes.SHELTER_HOME) { inclusive = true }
//                    }
//                }
//            )
        }

    }
}

@Composable
private fun ScreenView(
    viewModel: BaseScreenViewModel,
    view: @Composable () -> Unit
) {
    println("Navigating to ${viewModel::class.simpleName}")

    LaunchedEffect(Unit) {
        viewModel.onEnter()
    }

    view()
}

@Composable
private fun Loading(modifier: Modifier = Modifier) {
    BackHandler {}

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .blur(4.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 4.dp
        )
    }
}

@Composable
private fun ErrorBox(
    modifier: Modifier = Modifier, throwable: Throwable, onEvent: (MainViewEvents) -> Unit
) {
    AlertDialog(
        modifier = Modifier,
        onDismissRequest = { onEvent(MainViewEvents.ErrorBox.ClickedOk) },
        title = {
            Text(text = stringResource(R.string.error_title))
        },
        text = {
            Text(text = "${throwable.message}")
        },
        confirmButton = {
            TextButton(
                onClick = { onEvent(MainViewEvents.ErrorBox.ClickedOk) }
            ) {
                Text(text = stringResource(R.string.error_ok))
            }
        }
    )
}