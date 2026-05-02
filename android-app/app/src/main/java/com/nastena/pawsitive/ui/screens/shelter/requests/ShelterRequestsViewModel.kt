package com.nastena.pawsitive.ui.screens.shelter.requests

import android.util.Log
import com.nastena.pawsitive.dto.ShelterFormsResponse
import com.nastena.pawsitive.repository.FilesRepository
import com.nastena.pawsitive.repository.ShelterRepository
import com.nastena.pawsitive.ui.common.navigation.Navigation.To
import com.nastena.pawsitive.ui.common.navigation.NavigationRoute
import com.nastena.pawsitive.ui.common.navigation.NavigationRoute.ShelterFormDetails
import com.nastena.pawsitive.ui.common.navigation.NavigationRoute.ShelterRequests
import com.nastena.pawsitive.ui.main.MainViewModel
import com.nastena.pawsitive.ui.screens.BaseScreenViewModel
import com.nastena.pawsitive.ui.screens.shelter.requests.ShelterRequestsState.ConfirmForm
import com.nastena.pawsitive.ui.screens.shelter.requests.ShelterRequestsState.Form
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

    override val expectedRouteType: KClass<*> = ShelterRequests::class

    private val _formState: MutableStateFlow<List<ShelterRequestsState.Form>> =
        MutableStateFlow(emptyList())

    val formState: StateFlow<List<ShelterRequestsState.Form>> = _formState.asStateFlow()

    private val _confirmFormState: MutableStateFlow<ShelterRequestsState.ConfirmForm?> =
        MutableStateFlow(null)
    val confirmFormState: StateFlow<ShelterRequestsState.ConfirmForm?> =
        _confirmFormState.asStateFlow()

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
                    To(ShelterFormDetails(requestId = _requestIds[event.index]))
                )
            }

            is ShelterRequestsEvents.HideRequest -> {
                _confirmFormState.update { ConfirmForm(event.index) }
            }

            is ShelterRequestsEvents.ConfirmCancelClicked -> {
                val index: Int = _confirmFormState.value!!.requestIndex
                _confirmFormState.update { null }

                if (event.confirm) {
                    launchSave(
                        operation = {
                            _shelterRepository.hideRequest(_requestIds[index])
                        },
                        onSuccess = {
                            _formState.update { requests: List<Form> ->
                                requests.take(index) + requests.dropLast(index + 1)
                            }
                            _requestIds.removeAt(index)
                        }
                    )
                }
            }
        }
    }
}