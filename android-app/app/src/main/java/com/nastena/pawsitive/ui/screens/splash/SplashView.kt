package com.nastena.pawsitive.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.nastena.pawsitive.R
import kotlinx.coroutines.delay

@Composable
fun SplashView(modifier: Modifier = Modifier, viewModel: SplashViewModel) {

    val state by viewModel.state.collectAsState()

    var isAnimating by remember { mutableStateOf(true) }

    LaunchedEffect(isAnimating) {
        delay(1000)

        isAnimating = false
    }

    LaunchedEffect(state, isAnimating) {
        if (state == SplashState.Ready && !isAnimating) {
            viewModel.onViewEvent(SplashEvents.AnimationDone)
        } else if (state == SplashState.Loading && !isAnimating) {
            isAnimating = true
        }
    }

    Surface(
        modifier = modifier
            .fillMaxSize(),
        color = Color(0xFFFFFAF6) // to match an image background
    ) {
        Image(
            painter = painterResource(id = R.drawable.splash),
            contentDescription = "Splash screen",
            modifier = Modifier.fillMaxSize()
        )
    }
}
