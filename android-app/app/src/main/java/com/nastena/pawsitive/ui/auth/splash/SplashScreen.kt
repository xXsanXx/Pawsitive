package com.nastena.pawsitive.ui.auth.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.nastena.pawsitive.data.datastore.TokenManager
import com.nastena.pawsitive.data.remote.dto.Role

@Composable
fun SplashScreen(
    navController: NavController,
    tokenManager: TokenManager
) {

    LaunchedEffect(Unit) {

        val token = tokenManager.getToken()
        val role = tokenManager.getRole()

        if (token != null && role != null) {

            when (role) {
                Role.USER -> {
                    navController.navigate("user_home") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
                Role.SHELTER -> {
                    navController.navigate("shelter_home") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            }

        } else {
            navController.navigate("login") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Loading...")
    }
}