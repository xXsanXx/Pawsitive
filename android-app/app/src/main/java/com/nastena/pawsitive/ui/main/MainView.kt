package com.nastena.pawsitive.ui.main

import AnimalDetailsView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.nastena.pawsitive.R
import com.nastena.pawsitive.common.ServerParsedException
import com.nastena.pawsitive.common.ServerUnknownErrorCodeException
import com.nastena.pawsitive.dto.ErrorCode
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
import com.nastena.pawsitive.ui.screens.shelter.home.ShelterHomeView
import com.nastena.pawsitive.ui.screens.shelter.home.ShelterHomeViewModel
import com.nastena.pawsitive.ui.screens.shelter.home.ShelterHomeViewModelFactory
import com.nastena.pawsitive.ui.screens.shelter.profile.ShelterProfileView
import com.nastena.pawsitive.ui.screens.shelter.profile.ShelterProfileViewModel
import com.nastena.pawsitive.ui.screens.shelter.profile.ShelterProfileViewModelFactory
import com.nastena.pawsitive.ui.screens.shelter.profile.editing.EditingShelterProfileView
import com.nastena.pawsitive.ui.screens.shelter.profile.editing.EditingShelterProfileViewModel
import com.nastena.pawsitive.ui.screens.shelter.profile.editing.EditingShelterProfileViewModelFactory
import com.nastena.pawsitive.ui.screens.shelter.requests.ShelterRequestsView
import com.nastena.pawsitive.ui.screens.shelter.requests.ShelterRequestsViewModel
import com.nastena.pawsitive.ui.screens.shelter.requests.ShelterRequestsViewModelFactory
import com.nastena.pawsitive.ui.screens.shelter.requests.details.ShelterDetailsRequestsView
import com.nastena.pawsitive.ui.screens.shelter.requests.details.ShelterDetailsRequestsViewModel
import com.nastena.pawsitive.ui.screens.shelter.requests.details.ShelterDetailsRequestsViewModelFactory
import com.nastena.pawsitive.ui.screens.splash.SplashView
import com.nastena.pawsitive.ui.screens.splash.SplashViewModel
import com.nastena.pawsitive.ui.screens.splash.SplashViewModelFactory
import com.nastena.pawsitive.ui.screens.user.details.AnimalDetailsViewModel
import com.nastena.pawsitive.ui.screens.user.details.AnimalDetailsViewModelFactory
import com.nastena.pawsitive.ui.screens.user.details.form.FormView
import com.nastena.pawsitive.ui.screens.user.details.form.FormViewModel
import com.nastena.pawsitive.ui.screens.user.details.form.FormViewModelFactory
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
import kotlinx.coroutines.delay

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
        factory = UserProfileViewModelFactory(
            mainViewModel,
            userRepository,
            accountRepository,
            filesRepository
        )
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

    val shelterRequestsViewModel: ShelterRequestsViewModel = viewModel(
        factory = ShelterRequestsViewModelFactory(mainViewModel, shelterRepository, filesRepository)
    )

    val shelterDetailsRequestsViewModel: ShelterDetailsRequestsViewModel = viewModel(
        factory = ShelterDetailsRequestsViewModelFactory(
            mainViewModel,
            shelterRepository,
            filesRepository
        )
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

    val formViewModel: FormViewModel = viewModel(
        factory = FormViewModelFactory(mainViewModel, userRepository)
    )

    val shelterInfoViewModel: ShelterInfoViewModel = viewModel(
        factory = ShelterInfoViewModelFactory(mainViewModel, userRepository, filesRepository)
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
                    modifier = Modifier.height(50.dp),
                    navigationBarState = navigationBarState,
                    navController = navController
                )
            }

        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Navigation(
                    navController = navController,
                    currentViewModel = mainViewModel.currentViewModel,
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
                    shelterInfoViewModel = shelterInfoViewModel,
                    shelterRequestsViewModel = shelterRequestsViewModel,
                    shelterDetailsRequestsViewModel = shelterDetailsRequestsViewModel,
                    formViewModel = formViewModel,
                    onViewEvent = onViewEvent
                )
            }
        }

        var showLoading by remember { mutableStateOf(false) }

        LaunchedEffect(screenState) {
            if (screenState is MainState.Loading) {
                delay(300)
                showLoading = true
            } else {
                showLoading = false
            }
        }

        AnimatedVisibility(
            visible = showLoading,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Loading()
        }

        AnimatedVisibility(
            visible = screenState is MainState.Message
        ) {
            when (val currentState = screenState) {
                is MainState.Message -> {
                    MessageBox(
                        messageId = currentState.messageId,
                        onOkayClicked = { onViewEvent(MainViewEvents.MessageBox.ClickedOk) })
                }

                else -> {}
            }
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

    currentViewModel: BaseScreenViewModel?,

    splashViewModel: SplashViewModel,
    registerViewModel: RegisterViewModel,
    loginViewModel: LoginViewModel,

    userProfileViewModel: UserProfileViewModel,
    userHomeViewModel: UserHomeViewModel,
    userFavoriteViewModel: UserFavoriteViewModel,
    animalDetailsViewModel: AnimalDetailsViewModel,
    shelterInfoViewModel: ShelterInfoViewModel,
    formViewModel: FormViewModel,

    shelterProfileViewModel: ShelterProfileViewModel,
    editingShelterProfileViewModel: EditingShelterProfileViewModel,
    shelterHomeViewModel: ShelterHomeViewModel,
    shelterAnimalViewModel: ShelterAnimalViewModel,
    shelterRequestsViewModel: ShelterRequestsViewModel,
    shelterDetailsRequestsViewModel: ShelterDetailsRequestsViewModel,

    onViewEvent: (MainViewEvents) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoute.Splash
    ) {

        composable<NavigationRoute.Splash> { backStackEntry: NavBackStackEntry ->
            ScreenView(
                currentViewModel,
                splashViewModel,
                backStackEntry.toRoute<NavigationRoute.Splash>(),
                onViewEvent
            ) {
                SplashView(viewModel = splashViewModel)
            }
        }

        composable<NavigationRoute.Register> { backStackEntry: NavBackStackEntry ->
            ScreenView(
                currentViewModel,
                registerViewModel,
                backStackEntry.toRoute<NavigationRoute.Register>(),
                onViewEvent
            ) {
                RegisterView(viewModel = registerViewModel)
            }
        }

        composable<NavigationRoute.Login> { backStackEntry: NavBackStackEntry ->
            ScreenView(
                currentViewModel,
                loginViewModel,
                backStackEntry.toRoute<NavigationRoute.Login>(),
                onViewEvent
            ) {
                LoginView(viewModel = loginViewModel)
            }
        }

        composable<NavigationRoute.UserHome> { backStackEntry: NavBackStackEntry ->
            ScreenView(
                currentViewModel,
                userHomeViewModel,
                backStackEntry.toRoute<NavigationRoute.UserHome>(),
                onViewEvent
            ) {
                UserHomeView(viewModel = userHomeViewModel)
            }
        }

        composable<NavigationRoute.Favorite> { backStackEntry: NavBackStackEntry ->
            ScreenView(
                currentViewModel,
                userFavoriteViewModel,
                backStackEntry.toRoute<NavigationRoute.Favorite>(),
                onViewEvent
            ) {
                UserFavoriteView(viewModel = userFavoriteViewModel)
            }
        }

        composable<NavigationRoute.AnimalDetails> { backStackEntry: NavBackStackEntry ->
            ScreenView(
                currentViewModel,
                animalDetailsViewModel,
                backStackEntry.toRoute<NavigationRoute.AnimalDetails>(),
                onViewEvent
            ) {
                AnimalDetailsView(viewModel = animalDetailsViewModel)
            }
        }

        composable<NavigationRoute.ShelterInfo> { backStackEntry: NavBackStackEntry ->
            ScreenView(
                currentViewModel,
                shelterInfoViewModel,
                backStackEntry.toRoute<NavigationRoute.ShelterInfo>(),
                onViewEvent
            ) {
                ShelterInfoView(viewModel = shelterInfoViewModel)
            }
        }

        composable<NavigationRoute.Form> { backStackEntry: NavBackStackEntry ->
            ScreenView(
                currentViewModel,
                formViewModel,
                backStackEntry.toRoute<NavigationRoute.Form>(),
                onViewEvent
            ) {
                FormView(viewModel = formViewModel)
            }
        }

        composable<NavigationRoute.UserProfile> { backStackEntry: NavBackStackEntry ->
            ScreenView(
                currentViewModel,
                userProfileViewModel,
                backStackEntry.toRoute<NavigationRoute.UserProfile>(),
                onViewEvent
            ) {
                UserProfileView(viewModel = userProfileViewModel)
            }
        }

        composable<NavigationRoute.ShelterHome> { backStackEntry: NavBackStackEntry ->
            ScreenView(
                currentViewModel,
                shelterHomeViewModel,
                backStackEntry.toRoute<NavigationRoute.ShelterHome>(),
                onViewEvent
            ) {
                ShelterHomeView(viewModel = shelterHomeViewModel)
            }
        }

        composable<NavigationRoute.ShelterRequests> { backStackEntry: NavBackStackEntry ->
            ScreenView(
                currentViewModel,
                shelterRequestsViewModel,
                backStackEntry.toRoute<NavigationRoute.ShelterRequests>(),
                onViewEvent
            ) {
                ShelterRequestsView(viewModel = shelterRequestsViewModel)
            }
        }

        composable<NavigationRoute.ShelterFormDetails> { backStackEntry: NavBackStackEntry ->
            ScreenView(
                currentViewModel,
                shelterDetailsRequestsViewModel,
                backStackEntry.toRoute<NavigationRoute.ShelterFormDetails>(),
                onViewEvent
            ) {
                ShelterDetailsRequestsView(viewModel = shelterDetailsRequestsViewModel)
            }
        }

        composable<NavigationRoute.ShelterProfile> { backStackEntry: NavBackStackEntry ->
            ScreenView(
                currentViewModel,
                shelterProfileViewModel,
                backStackEntry.toRoute<NavigationRoute.ShelterProfile>(),
                onViewEvent
            ) {
                ShelterProfileView(viewModel = shelterProfileViewModel)
            }
        }

        composable<NavigationRoute.EditingShelterProfile> { backStackEntry ->
            ScreenView(
                currentViewModel,
                editingShelterProfileViewModel,
                backStackEntry.toRoute<NavigationRoute.EditingShelterProfile>(),
                onViewEvent
            ) {
                EditingShelterProfileView(viewModel = editingShelterProfileViewModel)
            }
        }

        composable<NavigationRoute.Shelter.Animal.Add> { backStackEntry: NavBackStackEntry ->
            ScreenView(
                currentViewModel,
                shelterAnimalViewModel,
                backStackEntry.toRoute<NavigationRoute.Shelter.Animal.Add>(),
                onViewEvent
            ) {
                ShelterAnimalView(viewModel = shelterAnimalViewModel)
            }
        }

        composable<NavigationRoute.Shelter.Animal.Edit> { backStackEntry: NavBackStackEntry ->
            ScreenView(
                currentViewModel,
                shelterAnimalViewModel,
                backStackEntry.toRoute<NavigationRoute.Shelter.Animal.Edit>(),
                onViewEvent
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
    navigationBarState: NavigationBarState
) {
    AnimatedVisibility(
        visible = navigationBarState.isVisible
    ) {
        Box(modifier = modifier) {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.background
            ) {
                val currentBackStackEntry: NavBackStackEntry? by navController.currentBackStackEntryAsState()

                navigationBarState.settings.items.forEachIndexed { index: Int, item: NavigationBars.Item ->
                    NavigationBarItem(
                        selected = currentBackStackEntry?.destination?.route == item.route::class.qualifiedName,
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = MaterialTheme.colorScheme.secondary
                        ),
                        onClick = {
                            navigate(navController, item.navigation)
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = null
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
    currentViewModel: BaseScreenViewModel?,
    viewModel: BaseScreenViewModel,
    route: NavigationRoute,
    onViewEvent: (MainViewEvents) -> Unit,
    view: @Composable () -> Unit
) {
    LaunchedEffect(Unit) {
        println("Navigating to ${viewModel::class.simpleName}")

        currentViewModel?.onExit()
        onViewEvent(MainViewEvents.CurrentViewModelChanged(viewModel))
        viewModel.onEnter(route)
    }

    view()
}

@Composable
private fun Loading(modifier: Modifier = Modifier) {
    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.1f))
                .blur(10.dp)
        )
        Box(
            modifier = modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            awaitPointerEvent()
                        }
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 4.dp
            )
        }
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
            val textId: Int = when (throwable) {
                is ServerParsedException -> {
                    when (throwable.errorCode) {
                        ErrorCode.UNKNOWN -> R.string.error_unknown
                        ErrorCode.UNAUTHORIZED -> R.string.error_unauthorized
                        ErrorCode.INVALID_REQUEST_BODY -> R.string.error_invalid_request_body
                        ErrorCode.LOGIN_CREDENTIALS_INVALID -> R.string.error_login_credentials_invalid
                        ErrorCode.REGISTER_CREDENTIALS_INVALID -> R.string.error_register_credentials_invalid
                        ErrorCode.USER_ALREADY_EXISTS -> R.string.error_user_already_exists
                        ErrorCode.INVALID_INPUT -> R.string.error_invalid_input
                        ErrorCode.INTERNAL_SERVER_ERROR -> R.string.error_internal_server_error
                    }
                }

                is ServerUnknownErrorCodeException -> {
                    if (throwable.httpCode == 403) {
                        R.string.error_unauthorized
                    } else {
                        R.string.error_unknown
                    }
                }

                else -> R.string.error_unknown
            }

            Text(text = stringResource(textId))
        },
        confirmButton = {
            Button(
                onClick = { onViewEvent(MainViewEvents.ErrorBox.ClickedOk) }
            ) {
                Text(text = stringResource(R.string.error_ok))
            }
        }
    )
}

@Composable
private fun MessageBox(
    modifier: Modifier = Modifier,
    messageId: Int,
    onOkayClicked: () -> Unit
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = { onOkayClicked() },
        text = {
            Text(text = stringResource(messageId))
        },
        confirmButton = {
            Button(
                onClick = { onOkayClicked() }
            ) {
                Text(text = stringResource(R.string.error_ok))
            }
        }
    )
}