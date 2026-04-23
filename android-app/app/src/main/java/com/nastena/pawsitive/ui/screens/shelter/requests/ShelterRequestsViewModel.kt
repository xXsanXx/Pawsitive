package com.nastena.pawsitive.ui.screens.shelter.requests

import com.nastena.pawsitive.repository.ShelterRepository
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
    private val _shelterRepository: ShelterRepository
) : BaseScreenViewModel(mainViewModel) {

    override val expectedRouteType: KClass<*> = NavigationRoute.ShelterRequests::class

    private val _animalsState: MutableStateFlow<List<ShelterRequestsState.Animal>> =
        MutableStateFlow(emptyList())

    val animalsState: StateFlow<List<ShelterRequestsState.Animal>> = _animalsState.asStateFlow()

    private val _animalIds: MutableList<Long> = mutableListOf()


    override fun onEnter(route: NavigationRoute) {
        super.onEnter(route)

        _animalIds.clear()
        _animalsState.update { emptyList() }

//        launchSave(
//            operation = {
//                Log.d("Requests", "Loading requests")
////                _shelterRepository.
//            },
//
//            onSuccess = { animalsResponse: AnimalsResponse ->
//                Log.i("Requests", "Got ${animalsResponse.animals.size} animals")
//
//                _animalsState.update {
//
//                    animalsResponse.animals.map { animalResponse ->
//                        _animalIds.add(animalResponse.id)
//                        ShelterRequestsState.Animal(
//                            name = animalResponse.name,
//                            photoUrl = animalResponse.animalPhotos?.getOrNull(0)
//                                ?.let { photo: String ->
//                                    NetworkUtils.getAbsoluteFileUrl(photo)
//                                }
//                        )
//                    }
//                }
//            }
//        )
    }
}