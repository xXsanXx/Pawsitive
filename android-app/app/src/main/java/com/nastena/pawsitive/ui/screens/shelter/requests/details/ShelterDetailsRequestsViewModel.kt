package com.nastena.pawsitive.ui.screens.shelter.requests.details

import android.util.Log
import com.nastena.pawsitive.dto.AdoptionStatus
import com.nastena.pawsitive.repository.FilesRepository
import com.nastena.pawsitive.repository.ShelterRepository
import com.nastena.pawsitive.ui.common.navigation.NavigationRoute
import com.nastena.pawsitive.ui.main.MainViewModel
import com.nastena.pawsitive.ui.screens.BaseScreenViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.reflect.KClass

class ShelterDetailsRequestsViewModel(
    mainViewModel: MainViewModel,
    private val _shelterRepository: ShelterRepository,
    private val _filesRepository: FilesRepository,

    ) : BaseScreenViewModel(mainViewModel) {

    override val expectedRouteType: KClass<*> = NavigationRoute.ShelterFormDetails::class

    private val _formState: MutableStateFlow<ShelterDetailsRequestsState.Form> = MutableStateFlow(
        ShelterDetailsRequestsState.Form(
            animalName = "",
            birthDate = 0,
            photoUrls = emptyList(),
            userName = "",
            phone = "",
            profession = "",
            status = AdoptionStatus.NONE
        )
    )

    val formState: StateFlow<ShelterDetailsRequestsState.Form> = _formState.asStateFlow()


    private var _requestId: Long = 0

    override fun onEnter(route: NavigationRoute) {
        super.onEnter(route)

        val detailsRoute = route as NavigationRoute.ShelterFormDetails

        _requestId = detailsRoute.requestId

        launchSave(
            operation = {
                Log.d("Details form", "Loading details form")
                _shelterRepository.getShelterFormDetails(resuestId = detailsRoute.requestId)
            },

            onSuccess = { response ->
                _formState.update {
                    ShelterDetailsRequestsState.Form(
                        animalName = response.animalName,
                        userName = response.userName,
                        phone = response.phone,
                        profession = response.profession,
                        birthDate = response.birthDate,
                        photoUrls = response.animalPhotos.map { url ->
                            _filesRepository.getAbsoluteFileUrl(url)
                        },
                        status = response.status
                    )
                }
            }
        )
    }

    fun onViewEvent(event: ShelterDetailsRequestsEvents) {
        when (event) {
            ShelterDetailsRequestsEvents.ApprovedClicked -> {
                updateStatus(AdoptionStatus.APPROVED)
            }

            ShelterDetailsRequestsEvents.RejectedClicked -> {
                updateStatus(AdoptionStatus.REJECTED)
            }
        }
    }


    private fun updateStatus(status: AdoptionStatus) {

        val requestId = _requestId

        launchSave(
            operation = {
                _shelterRepository.updateRequestStatus(requestId, status)
            },
            onSuccess = {
                _formState.update {
                    it.copy(status = status)
                }
            }
        )
    }
}