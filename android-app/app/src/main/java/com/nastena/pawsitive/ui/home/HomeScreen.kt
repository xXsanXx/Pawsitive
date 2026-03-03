package com.nastena.pawsitive.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onLogout: () -> Unit
) {
    val logoutState by viewModel.logoutState.collectAsState()

    LaunchedEffect(logoutState) {
        if (logoutState) {
            onLogout()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Добро пожаловать",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.logout() }
            ) {
                Text("Logout")
            }
        }
    }
}