package com.nastena.pawsitive.ui.main

import AnimalDetailsView
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
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.nastena.pawsitive.R
import com.nastena.pawsitive.repository.AccountRepository
import com.nastena.pawsitive.repository.FilesRepository
import com.nastena.pawsitive.repository.ShelterRepository
import com.nastena.pawsitive.repository.UserRepository
import com.nastena.pawsitive.ui.common.navigation.Navigation
import com.nastena.pawsitive.ui.common.navigation.NavigationBars
import com.nastena.pawsitive.ui.common.navigation.NavigationRoute
import com.nastena.pawsitive.ui.screens.BaseScreenViewModel
import com.nastena.pawsitive.ui.screens.login.LoginView
import com.nastena.pawsitive.ui.screens.login.LoginViewModel
import com.nastena.pawsitive.ui.screens.login.LoginViewModelFactory
import com.nastena.pawsitive.ui.screens.register.RegisterView
import com.nastena.pawsitive.ui.screens.register.RegisterViewModel
import com.nastena.pawsitive.ui.screens.register.RegisterViewModelFactory
import com.nastena.pawsitive.ui.screens.shelter.animal.ShelterAnimalView
import com.nastena.pawsitive.ui.screens.shelter.animal.ShelterAnimalViewModel
import com.nastena.pawsitive.ui.screens.shelter.animal.ShelterAnimalViewModelFactory
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
import com.nastena.pawsitive.ui.screens.user.details.AnimalDetailsViewModel
import com.nastena.pawsitive.ui.screens.user.details.AnimalDetailsViewModelFactory
import com.nastena.pawsitive.ui.screens.user.details.shelter_info.ShelterInfoView
import com.nastena.pawsitive.ui.screens.user.details.shelter_info.ShelterInfoViewModel
import com.nastena.pawsitive.ui.screens.user.details.shelter_info.ShelterInfoViewModelFactory
import com.nastena.pawsitive.ui.screens.user.favorite.UserFavoriteView
import com.nastena.pawsitive.ui.screens.user.favorite.UserFavoriteViewModel
import com.nastena.pawsitive.ui.screens.user.favorite.UserFavoriteViewModelFactory
import com.nastena.pawsitive.ui.screens.user.home.UserHomeView
import com.nastena.pawsitive.ui.screens.user.home.UserHomeViewModel
import com.nastena.pawsitive.ui.screens.user.home.UserHomeViewModelFactory
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

    val userProfileViewModel: UserProfileViewModel = viewModel(
        factory = UserProfileViewModelFactory(mainViewModel, userRepository, accountRepository)
    )

    val shelterProfileViewModel: ShelterProfileViewModel = viewModel(
        factory = ShelterProfileViewModelFactory(
            mainViewModel,
            shelterRepository,
            accountRepository
        )
    )

    val editingShelterProfileViewModel: EditingShelterProfileViewModel = viewModel(
        factory = EditingShelterProfileViewModelFactory(mainViewModel, shelterRepository)
    )

    val shelterHomeViewModel: ShelterHomeViewModel = viewModel(
        factory = ShelterHomeViewModelFactory(mainViewModel, shelterRepository, filesRepository)
    )

    val shelterAnimalViewModel: ShelterAnimalViewModel = viewModel(
        factory = ShelterAnimalViewModelFactory(mainViewModel, shelterRepository, filesRepository)
    )

    val userHomeViewModel: UserHomeViewModel = viewModel(
        factory = UserHomeViewModelFactory(mainViewModel, userRepository)
    )

    val userFavoriteViewModel: UserFavoriteViewModel = viewModel(
        factory = UserFavoriteViewModelFactory(mainViewModel, userRepository)
    )

    val animalDetailsViewModel: AnimalDetailsViewModel = viewModel(
        factory = AnimalDetailsViewModelFactory(mainViewModel, userRepository)
    )

    val shelterInfoViewModel: ShelterInfoViewModel = viewModel(
        factory = ShelterInfoViewModelFactory(mainViewModel, shelterRepository, filesRepository)
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

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
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
                    shelterAnimalViewModel = shelterAnimalViewModel,
                    userHomeViewModel = userHomeViewModel,
                    userFavoriteViewModel = userFavoriteViewModel,
                    animalDetailsViewModel = animalDetailsViewModel,
                    shelterInfoViewModel = shelterInfoViewModel
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
    userHomeViewModel: UserHomeViewModel,
    userFavoriteViewModel: UserFavoriteViewModel,
    animalDetailsViewModel: AnimalDetailsViewModel,
    shelterInfoViewModel: ShelterInfoViewModel,

    shelterProfileViewModel: ShelterProfileViewModel,
    editingShelterProfileViewModel: EditingShelterProfileViewModel,
    shelterHomeViewModel: ShelterHomeViewModel,
    shelterAnimalViewModel: ShelterAnimalViewModel
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoute.Splash
    ) {

        composable<NavigationRoute.Splash> { backStackEntry: NavBackStackEntry ->
            ScreenView(splashViewModel, backStackEntry.toRoute<NavigationRoute.Splash>()) {
                SplashView(viewModel = splashViewModel)
            }
        }

        composable<NavigationRoute.Register> { backStackEntry: NavBackStackEntry ->
            ScreenView(registerViewModel, backStackEntry.toRoute<NavigationRoute.Register>()) {
                RegisterView(viewModel = registerViewModel)
            }
        }

        composable<NavigationRoute.Login> { backStackEntry: NavBackStackEntry ->
            ScreenView(loginViewModel, backStackEntry.toRoute<NavigationRoute.Login>()) {
                LoginView(viewModel = loginViewModel)
            }
        }

        composable<NavigationRoute.UserHome> { backStackEntry: NavBackStackEntry ->
            ScreenView(userHomeViewModel, backStackEntry.toRoute<NavigationRoute.UserHome>()) {
                UserHomeView(viewModel = userHomeViewModel)
            }
        }

        composable<NavigationRoute.Favorite> { backStackEntry: NavBackStackEntry ->
            ScreenView(userFavoriteViewModel, backStackEntry.toRoute<NavigationRoute.Favorite>()) {
                UserFavoriteView(viewModel = userFavoriteViewModel)
            }
        }

        composable<NavigationRoute.AnimalDetails> { backStackEntry: NavBackStackEntry ->
            ScreenView(
                animalDetailsViewModel,
                backStackEntry.toRoute<NavigationRoute.AnimalDetails>()
            ) {
                AnimalDetailsView(viewModel = animalDetailsViewModel)
            }
        }

        composable<NavigationRoute.ShelterInfo> { backStackEntry: NavBackStackEntry ->
            ScreenView(
                shelterInfoViewModel,
                backStackEntry.toRoute<NavigationRoute.ShelterInfo>()
            ) {
                ShelterInfoView(viewModel = shelterInfoViewModel)
            }
        }

        composable<NavigationRoute.UserProfile> { backStackEntry: NavBackStackEntry ->
            ScreenView(
                userProfileViewModel,
                backStackEntry.toRoute<NavigationRoute.UserProfile>()
            ) {
                UserProfileView(viewModel = userProfileViewModel)
            }
        }

        composable<NavigationRoute.ShelterHome> { backStackEntry: NavBackStackEntry ->
            ScreenView(
                shelterHomeViewModel,
                backStackEntry.toRoute<NavigationRoute.ShelterHome>()
            ) {
                ShelterHomeView(viewModel = shelterHomeViewModel)
            }
        }

        composable<NavigationRoute.ShelterProfile> { backStackEntry: NavBackStackEntry ->
            ScreenView(
                shelterProfileViewModel,
                backStackEntry.toRoute<NavigationRoute.ShelterProfile>()
            ) {
                ShelterProfileView(viewModel = shelterProfileViewModel)
            }
        }

        composable<NavigationRoute.Shelter.Animal.Add> { backStackEntry: NavBackStackEntry ->
            ScreenView(
                shelterAnimalViewModel,
                backStackEntry.toRoute<NavigationRoute.Shelter.Animal.Add>()
            ) {
                ShelterAnimalView(viewModel = shelterAnimalViewModel)
            }
        }

        composable<NavigationRoute.Shelter.Animal.Edit> { backStackEntry: NavBackStackEntry ->
            ScreenView(
                shelterAnimalViewModel,
                backStackEntry.toRoute<NavigationRoute.Shelter.Animal.Edit>()
            ) {
                ShelterAnimalView(viewModel = shelterAnimalViewModel)
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
    route: NavigationRoute,
    view: @Composable () -> Unit
) {
    println("Navigating to ${viewModel::class.simpleName}")

    LaunchedEffect(Unit) {
        viewModel.onEnter(route)
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