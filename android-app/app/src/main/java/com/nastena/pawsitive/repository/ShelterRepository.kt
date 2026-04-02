package com.nastena.pawsitive.repository

import android.util.Log
import com.google.gson.Gson
import com.nastena.pawsitive.dto.AnimalBreed
import com.nastena.pawsitive.dto.AnimalGender
import com.nastena.pawsitive.dto.AnimalType
import com.nastena.pawsitive.dto.CreateAnimalRequest
import com.nastena.pawsitive.dto.ShelterAnimalsResponse
import com.nastena.pawsitive.dto.ShelterProfileResponse
import com.nastena.pawsitive.dto.UpdateShelterProfileRequest
import com.nastena.pawsitive.network.api.AnimalApi
import com.nastena.pawsitive.network.api.ShelterApi
import com.nastena.pawsitive.repository.utils.handleServerErrorBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File

class ShelterRepository(
    private val _api: ShelterApi,
    private val _animalsApi: AnimalApi
) {
    suspend fun getShelterProfileData(): Result<ShelterProfileResponse> = runCatching {
        val response: Response<ShelterProfileResponse> = _api.getShelterProfile()
        if (response.isSuccessful) {
            return Result.success(response.body()!!)
        } else {
            return handleServerErrorBody(response)
        }
    }

    suspend fun updateShelterProfileData(
        phone: String,
        address: String,
        info: String
    ): Result<Unit> = runCatching {
        val response: Response<Unit> =
            _api.updateShelterProfile(UpdateShelterProfileRequest(phone, address, info))
        if (response.isSuccessful) {
            return Result.success(Unit)
        } else {
            return handleServerErrorBody(response)
        }
    }

    suspend fun getShelterAnimalsData(): Result<ShelterAnimalsResponse> = runCatching {
        val response: Response<ShelterAnimalsResponse> = _animalsApi.getShelterAnimals()
        if (response.isSuccessful) {
            return Result.success(response.body()!!)
        } else {
            return handleServerErrorBody(response)
        }
    }

    suspend fun createAnimal(
        name: String,
        type: AnimalType,
        breed: AnimalBreed,
        gender: AnimalGender,
        description: String,
        birthDate: Long,
        photoPaths: List<String>,
        passportPaths: List<String>
    ): Result<Unit> = runCatching {
        Log.i("Shelter Repository", "[create animal] name: $name, type: $type, breed: $breed, " +
                "gender: $gender, description: $description, birthDate: $birthDate, photoPath: $photoPaths, passportPath: $passportPaths")

        val request = CreateAnimalRequest(
            name,
            type,
            breed,
            birthDate,
            gender,
            description
        )

        val gson = Gson()
        val json = gson.toJson(request)

        val data = json.toRequestBody(
            "application/json".toMediaType()
        )

        val photos = photoPaths.map { path ->
            val file = File(path)

            val requestFile = file.asRequestBody(
                "image/*".toMediaType()
            )

            MultipartBody.Part.createFormData(
                "photos",
                file.name,
                requestFile
            )
        }

        val passports = passportPaths.map { path ->

            val file = File(path)

            val requestFile = file.asRequestBody(
                "image/*".toMediaType()
            )

            MultipartBody.Part.createFormData(
                "vetPassports",
                file.name,
                requestFile
            )
        }

        val response = _animalsApi.createAnimal(
            data,
            photos,
            passports
        )

        if (response.isSuccessful) {
            Result.success(Unit)
        } else {
            handleServerErrorBody(response)
        }
    }

}