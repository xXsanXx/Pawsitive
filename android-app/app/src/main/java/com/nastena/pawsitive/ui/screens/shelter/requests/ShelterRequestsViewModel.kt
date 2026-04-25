package com.nastena.pawsitive.ui.screens.shelter.requests

import android.util.Log
import com.nastena.pawsitive.dto.ShelterFormsResponse
import com.nastena.pawsitive.repository.FilesRepository
import com.nastena.pawsitive.repository.ShelterRepository
import com.nastena.pawsitive.ui.common.navigation.Navigation.To
import com.nastena.pawsitive.ui.common.navigation.NavigationRoute
import com.nastena.pawsitive.ui.main.MainViewModel
import com.nastena.pawsitive.ui.screens.BaseScreenViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.reflect.KClass

class ShelterRequestsViewModel(
    mainViewModel: MainViewModel,
    private val _shelterRepository: ShelterRepository,
    private val _filesRepository: FilesRepository,

    ) : BaseScreenViewModel(mainViewModel) {

    override val expectedRouteType: KClass<*> = NavigationRoute.ShelterRequests::class

    private val _formState: MutableStateFlow<List<ShelterRequestsState.Form>> =
        MutableStateFlow(emptyList())

    val formState: StateFlow<List<ShelterRequestsState.Form>> = _formState.asStateFlow()

    private val _requestIds: MutableList<Long> = mutableListOf()


    override fun onEnter(route: NavigationRoute) {
        super.onEnter(route)

        _requestIds.clear()
        _formState.update { emptyList() }

        launchSave(
            operation = {
                Log.d("Requests", "Loading requests")
                _shelterRepository.getShelterForms()
            },

            onSuccess = { shelterFormsResponse: ShelterFormsResponse ->
                Log.i("Requests", "Got ${shelterFormsResponse.shelterFormsResponse.size} forms")

                _formState.update {
                    shelterFormsResponse.shelterFormsResponse.map { form ->
                        _requestIds.add(form.requestId)

                        ShelterRequestsState.Form(
                            animalName = form.animalName,
                            userName = form.userName,
                            photoUrls = form.animalPhotos.map { url ->
                                _filesRepository.getAbsoluteFileUrl(url)
                            },
                            status = form.status
                        )
                    }
                }
            }
        )
    }

    fun onViewEvent(event: ShelterRequestsEvents) {
        when (event) {
            is ShelterRequestsEvents.GoToFormClicked -> {
                mainViewModel.navigate(
                    To(NavigationRoute.ShelterFormDetails(requestId = _requestIds[event.index]))
                )
            }
        }
    }
}