package com.nastena.pawsitive.legacy_ui.shelter.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShelterHomeScreen(
    viewModel: ShelterHomeViewModel,
    onAddAnimal: () -> Unit,
    onLogout: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Shelter Panel")},
                actions = {
                    TextButton(onClick = onLogout) {
                        Text("Logout")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddAnimal) {
                Text("+")
            }
        }
    ) { padding ->

        when (state) {
            is ShelterHomeState.Success -> {
                val animals = (state as ShelterHomeState.Success).animals

                LazyColumn(
                    modifier = Modifier.padding(padding)
                ) {
                    items(animals) {animal ->
                        ShelterAnimalCard(animal)
                    }
                }
            }

            else -> {}
        }
    }
}