package com.nastena.pawsitive.ui.main

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nastena.pawsitive.R
import com.nastena.pawsitive.repository.AccountRepository
import com.nastena.pawsitive.repository.FilesRepository
import com.nastena.pawsitive.repository.ShelterRepository
import com.nastena.pawsitive.repository.UserRepository
import com.nastena.pawsitive.ui.common.navigation.Navigation
import com.nastena.pawsitive.ui.common.navigation.NavigationBars
import com.nastena.pawsitive.ui.common.navigation.NavigationRoutes
import com.nastena.pawsitive.ui.screens.BaseScreenViewModel
import com.nastena.pawsitive.ui.screens.login.LoginView
import com.nastena.pawsitive.ui.screens.login.LoginViewModel
import com.nastena.pawsitive.ui.screens.login.LoginViewModelFactory
import com.nastena.pawsitive.ui.screens.register.RegisterView
import com.nastena.pawsitive.ui.screens.register.RegisterViewModel
import com.nastena.pawsitive.ui.screens.register.RegisterViewModelFactory
import com.nastena.pawsitive.ui.screens.shelter.animal.add.ShelterAddAnimalView
import com.nastena.pawsitive.ui.screens.shelter.animal.add.ShelterAddAnimalViewModel
import com.nastena.pawsitive.ui.screens.shelter.animal.add.ShelterAddAnimalViewModelFactory
import com.nastena.pawsitive.ui.screens.shelter.editing.EditingShelterProfileView
import com.nastena.pawsitive.ui.screens.shelter.editing.EditingShelterProfileViewModel
import com.nastena.pawsitive.ui.screens.shelter.editing.EditingShelterProfileViewModelFactory
import com.nastena.pawsitive.ui.screens.shelter.home.ShelterHomeView
import com.nastena.pawsitive.ui.screens.shelter.home.ShelterHomeViewModel
import com.nastena.pawsitive.ui.screens.shelter.home.ShelterHomeViewModelFactory
import com.nastena.pawsitive.ui.screens.shelter.profile.ShelterProfileView
import com.nastena.pawsitive.ui.screens.shelter.profile.ShelterProfileViewModel
import com.nastena.pawsitive.ui.screens.shelter.profile.ShelterProfileViewModelFactory
import com.nastena.pawsitive.ui.screens.splash.SplashView
import com.nastena.pawsitive.ui.screens.splash.SplashViewModel
import com.nastena.pawsitive.ui.screens.splash.SplashViewModelFactory
import com.nastena.pawsitive.ui.screens.user.profile.UserProfileView
import com.nastena.pawsitive.ui.screens.user.profile.UserProfileViewModel
import com.nastena.pawsitive.ui.screens.user.profile.UserProfileViewModelFactory

@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    accountRepository: AccountRepository,
    userRepository: UserRepository,
    shelterRepository: ShelterRepository,
    filesRepository: FilesRepository,
) {

    val context = LocalContext.current
    val contentResolver = context.contentResolver

    val mainViewModel: MainViewModel = viewModel()

    val splashViewModel: SplashViewModel = viewModel(
        factory = SplashViewModelFactory(accountRepository, mainViewModel)
    )

    val loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(mainViewModel, accountRepository)
    )

    val registerViewModel: RegisterViewModel = viewModel(
        factory = RegisterViewModelFactory(mainViewModel, accountRepository)
    )

    val userProfileViewModel: UserProfileViewModel = viewModel (
        factory = UserProfileViewModelFactory(mainViewModel, userRepository, accountRepository )
    )

    val shelterProfileViewModel: ShelterProfileViewModel = viewModel (
        factory = ShelterProfileViewModelFactory(mainViewModel, shelterRepository, accountRepository )
    )

    val editingShelterProfileViewModel: EditingShelterProfileViewModel = viewModel (
        factory = EditingShelterProfileViewModelFactory(mainViewModel, shelterRepository)
    )

    val shelterHomeViewModel: ShelterHomeViewModel = viewModel (
        factory = ShelterHomeViewModelFactory(mainViewModel, shelterRepository, filesRepository)
    )

    val shelterAddAnimalViewModel: ShelterAddAnimalViewModel = viewModel (
        factory = ShelterAddAnimalViewModelFactory(mainViewModel, shelterRepository, _contentResolver = contentResolver )
    )

    val navController: NavHostController = rememberNavController()

    val screenState by mainViewModel.mainState.collectAsState()
    val navigationBarState by mainViewModel.navigationBarState.collectAsState()
    val onViewEvent: (MainViewEvents) -> Unit = { event -> mainViewModel.onViewEvent(event) }

    LaunchedEffect(Unit) {
        mainViewModel.navigationEvents.collect { navigation: Navigation ->
            navigate(navController, navigation)
        }
    }

    // --- Content --------

    Box(modifier = modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),

            bottomBar = {
                MainNavigationBar(
                    navigationBarState = navigationBarState,
                    navController = navController,
                    onViewEvent = onViewEvent
                )
            }

        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                Navigation(
                    navController = navController,
                    splashViewModel = splashViewModel,
                    registerViewModel = registerViewModel,
                    loginViewModel = loginViewModel,
                    userProfileViewModel = userProfileViewModel,
                    shelterProfileViewModel = shelterProfileViewModel,
                    editingShelterProfileViewModel = editingShelterProfileViewModel,
                    shelterHomeViewModel = shelterHomeViewModel,
                    shelterAddAnimalViewModel = shelterAddAnimalViewModel
                )
            }
        }

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
                ErrorBox(throwable = errorState.throwable, onViewEvent = onViewEvent)
            }
        }
    }
}

@Composable
private fun Navigation(
    navController: NavHostController,
    splashViewModel: SplashViewModel,
    registerViewModel: RegisterViewModel,
    loginViewModel: LoginViewModel,
    userProfileViewModel: UserProfileViewModel,
    shelterProfileViewModel: ShelterProfileViewModel,
    editingShelterProfileViewModel: EditingShelterProfileViewModel,
    shelterHomeViewModel: ShelterHomeViewModel,
    shelterAddAnimalViewModel: ShelterAddAnimalViewModel
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
            ScreenView(registerViewModel) {
                RegisterView(viewModel = registerViewModel)
            }
        }

        composable(NavigationRoutes.LOGIN) {
            ScreenView(loginViewModel) {
                LoginView(viewModel = loginViewModel)
            }
        }

        composable(NavigationRoutes.USER_HOME) {
            Text("User home")
        }

        composable(NavigationRoutes.USER_PROFILE) {
            ScreenView(userProfileViewModel) {
                UserProfileView(viewModel = userProfileViewModel)
            }
        }

        composable(NavigationRoutes.SHELTER_HOME){
            ScreenView(shelterHomeViewModel) {
                ShelterHomeView(viewModel = shelterHomeViewModel)
            }
        }

        composable(NavigationRoutes.SHELTER_PROFILE) {
            ScreenView(shelterProfileViewModel) {
                ShelterProfileView(viewModel = shelterProfileViewModel)
            }
        }

        composable(NavigationRoutes.SHELTER_PROFILE_EDITING) {
            ScreenView(editingShelterProfileViewModel) {
                EditingShelterProfileView(viewModel = editingShelterProfileViewModel)
            }
        }

        composable(NavigationRoutes.SHELTER_ADD_ANIMAL) {
            ScreenView(shelterAddAnimalViewModel) {
                ShelterAddAnimalView(viewModel = shelterAddAnimalViewModel)
            }
        }
    }
}

@Composable
private fun MainNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    navigationBarState: NavigationBarState,
    onViewEvent: (MainViewEvents) -> Unit
) {
    AnimatedVisibility(
        visible = navigationBarState.isVisible
    ) {
        Box(modifier = modifier) {
            NavigationBar {
                navigationBarState.settings.items.forEachIndexed { index: Int, item: NavigationBars.Item ->
                    NavigationBarItem(
                        selected = index == navigationBarState.selected,
                        onClick = {
                            onViewEvent(MainViewEvents.NavigationBar.ClickedItem(index))
                            navigate(navController, item.navigation)
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = null // TODO: add content description everywhere
                            )
                        }
                    )
                }
            }
        }
    }
}

private fun navigate(
    navController: NavHostController,
    navigation: Navigation
) {
    when (navigation) {
        is Navigation.To -> {
            navController.navigate(navigation.route) {
                when (val popUpType = navigation.popUpType) {
                    Navigation.To.PopUpType.None -> {}
                    Navigation.To.PopUpType.Origin -> {
                        popUpTo(0)
                    }

                    is Navigation.To.PopUpType.Route -> {
                        popUpTo(popUpType.route) { inclusive = true }
                    }
                }
            }
        }

        Navigation.Back -> {
            navController.popBackStack()
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
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 4.dp
        )
    }
}

@Composable
private fun ErrorBox(
    modifier: Modifier = Modifier, throwable: Throwable, onViewEvent: (MainViewEvents) -> Unit
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = { onViewEvent(MainViewEvents.ErrorBox.ClickedOk) },
        title = {
            Text(text = stringResource(R.string.error_title))
        },
        text = {
            Text(text = "${throwable.message}")
        },
        confirmButton = {
            TextButton(
                onClick = { onViewEvent(MainViewEvents.ErrorBox.ClickedOk) }
            ) {
                Text(text = stringResource(R.string.error_ok))
            }
        }
    )
}