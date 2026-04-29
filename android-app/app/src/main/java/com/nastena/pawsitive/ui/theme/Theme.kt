package com.nastena.pawsitive.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorsScheme = lightColorScheme(

    primary = PrimaryOrange,
    secondary = SecondarySand,

    background = BackgroundCream,
    surface = BackgroundCream,

    onPrimary = TextPrimary,
    onBackground = TextPrimary,
    onSurface = TextPrimary,

    error = DangerCoral
)

private val DarkColorsScheme = darkColorScheme(

    primary = PrimaryOrange,
    secondary = SecondarySand,

    background = Color(0xFF1A1A1A),
    surface = Color(0xFF1A1A1A),

    onPrimary = Color.Black,
    onSecondary = Color.Black,

    onBackground = Color.White,
    onSurface = Color.White,

    error = DangerCoral
)

@Composable
fun PawsitiveTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorsScheme
        else -> LightColorsScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}