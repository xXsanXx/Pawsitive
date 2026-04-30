package com.nastena.pawsitive.ui.screens.user.details.shelter_info

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.nastena.pawsitive.R
import com.nastena.pawsitive.ui.common.localization.LocalizationUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShelterInfoView(
    modifier: Modifier = Modifier,
    viewModel: ShelterInfoViewModel
) {

    val animalsState by viewModel.animalsState.collectAsState()
    val shelterState by viewModel.shelterState.collectAsState()

    Scaffold(
        modifier = modifier.fillMaxSize(),

        topBar = {
            androidx.compose.material3.TopAppBar(
                title = { Text(stringResource(R.string.shelter_info_label)) },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            viewModel.onViewEvent(ShelterInfoEvents.BackClicked)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = null
                        )
                    }
                }
            )
        }

    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            item {

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Pets, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = shelterState.name,
                                style = MaterialTheme.typography.titleLarge
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Email, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = shelterState.email)
                        }

                        ShelterInfoRow(Icons.Default.Phone, shelterState.phone)

                        ShelterInfoRow(Icons.Default.LocationOn, shelterState.address)

                        Text(text = shelterState.info)
                    }
                }
            }

            items(animalsState.size) { index ->

                val animal = animalsState[index]

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        if (animal.photoUrls.isNotEmpty()) {

                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(animal.photoUrls.first())
                                    .transformations(CircleCropTransformation())
                                    .build(),
                                contentDescription = null,
                                modifier = Modifier.size(72.dp),
                                error = painterResource(R.drawable.ic_image_error)
                            )
                        }

                        Spacer(modifier = Modifier.size(12.dp))

                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {

                            Text(
                                text = animal.name,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Text(
                                text = stringResource(
                                    LocalizationUtils.getAnimalTypeStringId(animal.type)
                                )
                            )

                            Text(
                                text = "${animal.age} ${stringResource(R.string.common_years)}"
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun ShelterInfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String?
) {
    if (!text.isNullOrBlank()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text)
        }
    }
}
