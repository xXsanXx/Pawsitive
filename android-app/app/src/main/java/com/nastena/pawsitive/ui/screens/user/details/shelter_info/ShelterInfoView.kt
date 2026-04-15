package com.nastena.pawsitive.ui.screens.user.details.shelter_info

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
            IconButton(
                onClick = {
                    viewModel.onViewEvent(
                        ShelterInfoEvents.BackClicked
                    )
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = null
                )
            }
        }
    ) { paddingValues ->
        Column {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Text(text = shelterState.name)

                Text(text = shelterState.email)

                Text(text = shelterState.phone)

                Text(text = shelterState.address)

                Text(text = shelterState.info)


            }

            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .scrollable(rememberScrollState(), Orientation.Vertical)
                    .fillMaxWidth()
            ) {
                items(animalsState.size) { index: Int ->
                    val animalState: ShelterInfoState.Animal = animalsState[index]

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (animalState.photoUrls.isNotEmpty()) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(animalState.photoUrls[0])
                                    .transformations(CircleCropTransformation())
                                    .build(),
                                contentDescription = null,
                                modifier = Modifier.size(80.dp),
                                error = painterResource(R.drawable.ic_image_error)
                            )
                        }

                        Column(
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(text = animalState.name)
                            Text(
                                text = stringResource(
                                    LocalizationUtils.getAnimalTypeStringId(
                                        animalState.type
                                    )
                                )
                            )
                            Text(text = "${animalState.age} ${stringResource(R.string.common_years)}")
                        }

                        Spacer(modifier = Modifier.height(12.dp))


                    }
                }
            }
        }
    }
}
