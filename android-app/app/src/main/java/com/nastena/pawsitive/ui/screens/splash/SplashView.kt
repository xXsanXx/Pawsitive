package com.nastena.pawsitive.ui.screens.splash

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SplashView(modifier: Modifier = Modifier, viewModel: SplashViewModel) {
    Surface(modifier = modifier, color = MaterialTheme.colorScheme.background) {
        CircularProgressIndicator()
    }
}