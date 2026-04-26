package com.nastena.pawsitive.repository

import com.nastena.pawsitive.dto.AnimalResponse
import com.nastena.pawsitive.dto.AnimalsResponse
import com.nastena.pawsitive.dto.ShelterInfoResponse
import com.nastena.pawsitive.dto.UserAdoptionsResponse
import com.nastena.pawsitive.dto.UserFormResponse
import com.nastena.pawsitive.dto.UserFormUpdateRequest
import com.nastena.pawsitive.dto.UserProfileResponse
import com.nastena.pawsitive.network.api.AdoptionApi
import com.nastena.pawsitive.network.api.AnimalApi
import com.nastena.pawsitive.network.api.FavoriteApi
import com.nastena.pawsitive.network.api.ShelterApi
import com.nastena.pawsitive.network.api.UserApi
import com.nastena.pawsitive.repository.utils.runSimpleRequest

class UserRepository(
    private val _api: UserApi,
    private val _shelterApi: ShelterApi,
    private val _animalApi: AnimalApi,
    private val _favoriteApi: FavoriteApi,
    private val _adoptionApi: AdoptionApi
) {
    suspend fun getProfileData(): Result<UserProfileResponse> = runSimpleRequest {
        _api.getUserProfile()
    }

    suspend fun getRandomAnimalsRation(): Result<AnimalsResponse> = runSimpleRequest {
        _animalApi.getRandomUserAnimalsRation()
    }

    suspend fun addToFavorite(animalId: Long): Result<Unit> = runSimpleRequest {
        _favoriteApi.add(animalId)
    }

    suspend fun removeFromFavorite(animalId: Long): Result<Unit> = runSimpleRequest {
        _favoriteApi.remove(animalId)
    }

    suspend fun getFavorites(): Result<AnimalsResponse> = runSimpleRequest { _favoriteApi.get() }

    suspend fun getAnimalDetails(animalId: Long): Result<AnimalResponse> = runSimpleRequest {
        _animalApi.getAnimalDetails(animalId)
    }

    suspend fun getShelterInfo(shelterId: Long): Result<ShelterInfoResponse> = runSimpleRequest {
        _shelterApi.getShelterInfo(shelterId)
    }

    suspend fun updateForm(
        name: String,
        birthDate: Long,
        profession: String,
        currentPets: String,
        previousPets: String,
        feedingExperience: String,
        vaccination: String,
        reason: String,
        petCareWhenAway: String,
        problemCharacter: String,
        healthIssues: String,
        additionalInfo: String,
        phone: String
    ): Result<Unit> = runSimpleRequest {
        _api.updateUserForm(
            userFormUpdateRequest = UserFormUpdateRequest(
                name,
                birthDate,
                profession,
                currentPets,
                previousPets,
                feedingExperience,
                vaccination,
                reason,
                petCareWhenAway,
                problemCharacter,
                healthIssues,
                additionalInfo,
                phone
            )
        )
    }

    suspend fun getFormForAnimal(animalId: Long): Result<UserFormResponse> = runSimpleRequest {
        _api.getUserFormForAnimal(animalId)
    }

    suspend fun createForm(id: Long): Result<Unit> = runSimpleRequest {
        _adoptionApi.createForm(id)
    }

    suspend fun getUserRequests(): Result<UserAdoptionsResponse> = runSimpleRequest {
        _adoptionApi.getUserRequests()
    }

    suspend fun cancelAdoptionRequest(animalId: Long): Result<Unit> = runSimpleRequest {
        _adoptionApi.cancelAdoptionRequest(animalId)

    }
}