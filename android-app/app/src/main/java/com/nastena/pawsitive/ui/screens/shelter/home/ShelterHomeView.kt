package com.nastena.pawsitive.ui.screens.shelter.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.nastena.pawsitive.R
import com.nastena.pawsitive.ui.common.localization.LocalizationUtils

@Composable
fun ShelterHomeView(
    modifier: Modifier = Modifier,
    viewModel: ShelterHomeViewModel
) {
    val animalsState by viewModel.animalsState.collectAsState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            IconButton(onClick = { viewModel.onViewEvent(event = ShelterHomeEvents.AddAnimalClicked) }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    ) { paddingValues: PaddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues).fillMaxWidth()
        ) {
            items(animalsState.size) { index: Int ->
                val animalState: ShelterHomeState.Animal = animalsState[index]

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(text = animalState.name)
                        Text(text = stringResource(LocalizationUtils.getAnimalTypeStringId(animalState.type)))
                        Text(text = "${animalState.age} ${stringResource(R.string.common_years)}")
                    }
                    IconButton(onClick = { viewModel.onViewEvent(event = ShelterHomeEvents.EditingClicked(index) )}) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null
                        )
                    }
                }
            }
        }

    }

}