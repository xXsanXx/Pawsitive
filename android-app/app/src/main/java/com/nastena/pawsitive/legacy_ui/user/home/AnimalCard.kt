package com.nastena.pawsitive.legacy_ui.user.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nastena.pawsitive.legacy_ui.model.AnimalUi

@Composable
fun AnimalCard(animal: AnimalUi) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(animal.name, style = MaterialTheme.typography.titleLarge)
            Text(animal.subtitle)
        }
    }
}