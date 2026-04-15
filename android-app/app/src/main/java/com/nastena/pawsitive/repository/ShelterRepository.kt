package com.nastena.pawsitive.repository

import android.content.ContentResolver
import com.nastena.pawsitive.dto.AnimalBreed
import com.nastena.pawsitive.dto.AnimalGender
import com.nastena.pawsitive.dto.AnimalType
import com.nastena.pawsitive.dto.CreateAnimalRequest
import com.nastena.pawsitive.dto.ShelterAnimalResponse
import com.nastena.pawsitive.dto.ShelterAnimalsResponse
import com.nastena.pawsitive.dto.ShelterProfileResponse
import com.nastena.pawsitive.dto.UpdateAnimalRequest
import com.nastena.pawsitive.dto.UpdateShelterProfileRequest
import com.nastena.pawsitive.network.NetworkUtils
import com.nastena.pawsitive.network.api.AnimalApi
import com.nastena.pawsitive.network.api.ShelterApi
import com.nastena.pawsitive.repository.utils.handleServerErrorBody
import com.nastena.pawsitive.repository.utils.runSimpleRequest
import com.nastena.pawsitive.utils.AnimalUtils
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class ShelterRepository(
    private val _api: ShelterApi,
    private val _animalsApi: AnimalApi,
    private val _contentResolver: ContentResolver
) {
    suspend fun getProfileData(): Result<ShelterProfileResponse> = runSimpleRequest {
        _api.getShelterProfile()
    }

    suspend fun updateProfileData(
        phone: String,
        address: String,
        info: String
    ): Result<Unit> = runSimpleRequest {
        _api.updateShelterProfile(UpdateShelterProfileRequest(phone, address, info))
    }

    suspend fun getAnimalsData(): Result<ShelterAnimalsResponse> = runSimpleRequest {
        _animalsApi.getShelterAnimals()
    }

    suspend fun getAnimal(animalId: Long): Result<ShelterAnimalResponse> = runSimpleRequest {
        _animalsApi.getShelterAnimal(animalId)
    }

    suspend fun createAnimal(
        name: String,
        type: AnimalType,
        breed: AnimalBreed,
        gender: AnimalGender,
        description: String,
        birthDate: Long,
        animalPhotoUris: List<String>,
        passportPhotoUris: List<String>,
    ): Result<Unit> = runCatching {

        val request = CreateAnimalRequest(
            name, type, breed, birthDate, gender, description
        )

        val data: RequestBody = NetworkUtils.dtoToRequestBody(request)

        val photos: List<MultipartBody.Part> = animalPhotoUris.map { uriString ->
            NetworkUtils.photoUriToMultipart(
                uriString, AnimalUtils.RequestParams.ANIMAL_PHOTOS, _contentResolver
            )
        }

        val passports: List<MultipartBody.Part> = passportPhotoUris.map { uriString ->
            NetworkUtils.photoUriToMultipart(
                uriString, AnimalUtils.RequestParams.PASSPORT_PHOTOS, _contentResolver
            )
        }

        val response: Response<Long> = _animalsApi.createAnimal(data, photos, passports)

        if (response.isSuccessful) {
            Result.success(Unit)
        } else {
            handleServerErrorBody(response)
        }
    }

    suspend fun updateAnimal(
        id: Long,
        name: String,
        type: AnimalType,
        breed: AnimalBreed,
        gender: AnimalGender,
        description: String,
        birthDate: Long,
        removedAnimalPhotos: List<String>,
        newPhotoUris: List<String>,
        removedPassportPhotos: List<String>,
        newPassportUris: List<String>,
    ): Result<Unit> = runCatching {

        val request = UpdateAnimalRequest(
            id,
            name,
            type,
            breed,
            birthDate,
            gender,
            description,
            removedAnimalPhotos,
            removedPassportPhotos
        )

        val data: RequestBody = NetworkUtils.dtoToRequestBody(request)

        val photos: List<MultipartBody.Part> = newPhotoUris.map { uriString ->
            NetworkUtils.photoUriToMultipart(
                uriString, AnimalUtils.RequestParams.ANIMAL_PHOTOS, _contentResolver
            )
        }

        val passports: List<MultipartBody.Part> = newPassportUris.map { uriString ->
            NetworkUtils.photoUriToMultipart(
                uriString, AnimalUtils.RequestParams.PASSPORT_PHOTOS, _contentResolver
            )
        }

        val response: Response<Unit> = _animalsApi.updateAnimal(data, photos, passports)

        if (response.isSuccessful) {
            Result.success(Unit)
        } else {
            handleServerErrorBody(response)
        }
    }

    suspend fun removeAnimal(animalId: Long): Result<Unit> = runSimpleRequest {
        _animalsApi.removeAnimal(animalId)
    }


}


