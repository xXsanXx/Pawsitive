package com.nastena.pawsitive.ui.auth.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.nastena.pawsitive.data.datastore.TokenManager

@Composable
fun SplashScreen(
    tokenManager: TokenManager,
    onAuthorized: () -> Unit,
    onUnauthorized: () -> Unit
) {
    LaunchedEffect(Unit) {
        val token = tokenManager.getToken()

        if (token.isNullOrEmpty()) {
            onUnauthorized()
        } else {
            onAuthorized()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}