import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nastena.pawsitive.R
import com.nastena.pawsitive.dto.AdoptionStatus
import com.nastena.pawsitive.dto.AnimalGender
import com.nastena.pawsitive.dto.AnimalType
import com.nastena.pawsitive.ui.common.AnimalImage
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
        modifier = modifier.fillMaxSize(),
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = animalState.name,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
        ) {

            item {

                AnimalPhotoGallery(
                    photos = animalState.photosUrl,
                    passportPhotos = animalState.passportPhotosUrl,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                )
            }

            item {

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.large,
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        )
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {

                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                                Text(
                                    text = when (animalState.type) {
                                        AnimalType.DOG -> "🐶"
                                        AnimalType.CAT -> "🐱"
                                    }
                                )

                                Text(
                                    text = stringResource(
                                        LocalizationUtils.getAnimalTypeStringId(animalState.type)
                                    )
                                )
                            }

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "🎂"
                                )

                                Text(
                                    text = stringResource(
                                        R.string.animal_birth_date,
                                        Utils.formatDate(animalState.birthDate)
                                    )
                                )
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                                Text(
                                    text = "🐾"
                                )

                                Text(
                                    text = stringResource(
                                        LocalizationUtils.getAnimalBreedStringId(animalState.breed)
                                    )
                                )
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                                Text(
                                    text = when (animalState.gender) {
                                        AnimalGender.MALE -> "♂️"
                                        AnimalGender.FEMALE -> "♀️"
                                    }
                                )

                                Text(
                                    text = stringResource(
                                        LocalizationUtils.getAnimalGenderStringId(animalState.gender)
                                    )
                                )
                            }
                            Text(text = animalState.description)


                        }
                    }
                }
            }

            item {

                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Button(
                        onClick = {
                            viewModel.onViewEvent(AnimalDetailsEvents.ShelterInfoClicked)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.shelter_info_clicked))
                    }

                    val isFormRequestEnabled = animalState.adoptionStatus == AdoptionStatus.NONE ||
                            animalState.adoptionStatus == AdoptionStatus.CANCELED

                    Button(
                        onClick = {
                            viewModel.onViewEvent(AnimalDetailsEvents.GoToFormClicked)
                        },
                        colors = ButtonDefaults.buttonColors(),
                        modifier = Modifier.fillMaxWidth(),
                        enabled = isFormRequestEnabled
                    ) {

                        Text(
                            text = if (isFormRequestEnabled) {
                                stringResource(R.string.form_clicked)
                            } else {
                                when (animalState.adoptionStatus) {
                                    AdoptionStatus.PENDING ->
                                        stringResource(R.string.adoption_status_pending)

                                    AdoptionStatus.APPROVED ->
                                        stringResource(R.string.adoption_status_approved)

                                    AdoptionStatus.REJECTED ->
                                        stringResource(R.string.adoption_status_rejected)

                                    else -> ""
                                }
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
private fun AnimalPhotoGallery(
    photos: List<String>,
    passportPhotos: List<String>,
    modifier: Modifier = Modifier
) {
    val allPhotos = photos + passportPhotos

    if (allPhotos.isEmpty()) return

    val pagerState = rememberPagerState(
        pageCount = { allPhotos.size }
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 32.dp),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) { page ->
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    AnimalImage(
                        photoUrl = allPhotos[page],
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            repeat(allPhotos.size) { index ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(
                            width = if (pagerState.currentPage == index) 24.dp else 8.dp,
                            height = 8.dp
                        )
                        .clip(CircleShape)
                        .background(
                            if (pagerState.currentPage == index)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                        )
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "${pagerState.currentPage + 1} / ${allPhotos.size}",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}