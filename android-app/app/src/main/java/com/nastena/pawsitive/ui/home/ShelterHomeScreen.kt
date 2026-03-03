package com.nastena.pawsitive.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

@Composable
fun ShelterHomeScreen(onLogout: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Shelter home")
        Button(onClick = onLogout) {
            Text("Logout")
        }
    }
}