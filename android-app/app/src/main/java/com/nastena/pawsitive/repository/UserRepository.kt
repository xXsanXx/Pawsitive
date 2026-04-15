package com.nastena.pawsitive.repository

import com.nastena.pawsitive.dto.AnimalResponse
import com.nastena.pawsitive.dto.AnimalsResponse
import com.nastena.pawsitive.dto.ShelterInfoResponse
import com.nastena.pawsitive.dto.UserProfileResponse
import com.nastena.pawsitive.network.api.AnimalApi
import com.nastena.pawsitive.network.api.FavoriteApi
import com.nastena.pawsitive.network.api.ShelterApi
import com.nastena.pawsitive.network.api.UserApi
import com.nastena.pawsitive.repository.utils.runSimpleRequest

class UserRepository(
    private val _api: UserApi,
    private val _shelterApi: ShelterApi,
    private val _animalApi: AnimalApi,
    private val _favoriteApi: FavoriteApi
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
}