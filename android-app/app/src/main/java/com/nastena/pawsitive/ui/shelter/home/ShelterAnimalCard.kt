package com.nastena.pawsitive.ui.shelter.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.unit.dp
import com.nastena.pawsitive.ui.model.AnimalUi

@Composable
fun ShelterAnimalCard(animal: AnimalUi) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(animal.name)
            Text(animal.subtitle)

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = { /* edit */}) {
                Text("Редактировать")
            }
        }
    }
}