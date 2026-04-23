import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nastena.pawsitive.R
import com.nastena.pawsitive.dto.AdoptionStatus
import com.nastena.pawsitive.ui.common.Utils
import com.nastena.pawsitive.ui.common.localization.LocalizationUtils
import com.nastena.pawsitive.ui.screens.user.details.AnimalDetailsEvents
import com.nastena.pawsitive.ui.screens.user.details.AnimalDetailsViewModel


@Composable
fun AnimalDetailsView(
    modifier: Modifier = Modifier,
    viewModel: AnimalDetailsViewModel
) {

    val animalState by viewModel.animalState.collectAsState()

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
        ) {

            item {

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    items(animalState.photosUrl) { photos ->

                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(photos)
                                .build(),
                            contentDescription = null,
                            modifier = Modifier.size(200.dp),
                            error = painterResource(R.drawable.ic_image_error)
                        )
                    }
                }
            }

            item {

                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Text(text = animalState.name)

                    Text(
                        text = stringResource(
                            LocalizationUtils.getAnimalTypeStringId(animalState.type)
                        )
                    )
                    Text(
                        text = stringResource(
                            R.string.animal_birth_date,
                            Utils.formatDate(animalState.birthDate),
                        )
                    )

                    Text(
                        text = stringResource(
                            LocalizationUtils.getAnimalBreedStringId(animalState.breed)
                        )
                    )

                    Text(
                        text = stringResource(
                            LocalizationUtils.getAnimalGenderStringId(animalState.gender)
                        )
                    )

                    Button(
                        onClick = {
                            viewModel.onViewEvent(
                                AnimalDetailsEvents.ShelterInfoClicked
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            stringResource(R.string.shelter_info_clicked)
                        )
                    }

                    if (animalState.adoptionStatus == AdoptionStatus.NONE) {
                        Button(
                            onClick = {
                                viewModel.onViewEvent(
                                    AnimalDetailsEvents.GoToFormClicked
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(stringResource(R.string.form_clicked))
                        }
                    } else {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = when (animalState.adoptionStatus) {
                                AdoptionStatus.PENDING -> stringResource(R.string.adoption_status_pending)
                                AdoptionStatus.APPROVED -> stringResource(R.string.adoption_status_approved)
                                AdoptionStatus.REJECTED -> stringResource(R.string.adoption_status_rejected)
                                else -> ""
                            },
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }

    }
}