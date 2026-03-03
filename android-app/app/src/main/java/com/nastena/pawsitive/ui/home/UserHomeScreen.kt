package com.nastena.pawsitive.ui.home

import android.widget.Button
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

@Composable
fun UserHomeScreen(onLogout: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("User home")
        Button(onClick = onLogout) {
            Text("Logout")
        }
    }
}