package com.bezubaan.app.feature.rescue.data.repository

import com.bezubaan.app.core.common.Resource
import com.bezubaan.app.feature.rescue.data.remote.RescueApi
import com.bezubaan.app.feature.rescue.domain.model.RescueCase
import com.bezubaan.app.feature.rescue.domain.repository.RescueRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

import com.bezubaan.app.feature.rescue.data.remote.toDomain
import com.bezubaan.app.feature.rescue.data.remote.CreateRescueRequest
import com.bezubaan.app.feature.rescue.data.remote.AnimalDto

class RescueRepositoryImpl @Inject constructor(
    private val api: RescueApi
) : RescueRepository {

    override fun getNearbyRescues(lat: Double, lng: Double): Flow<Resource<List<RescueCase>>> = flow {
        emit(Resource.Loading)
        try {
            val response = api.getNearbyRescues(lat, lng)
            if (response.isSuccessful && response.body() != null) {
                val data = response.body()!!.map { it.toDomain() }
                emit(Resource.Success(data))
            } else {
                emit(Resource.Error(response.message() ?: "Failed to fetch nearby rescues"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error occurred"))
        }
    }

    override fun reportRescue(
        description: String,
        lat: Double,
        lng: Double,
        address: String?,
        animalSpecies: String,
        animalBreed: String?,
        animalSex: String?,
        animalAge: String?,
        animalColor: String?,
        animalSize: String?
    ): Flow<Resource<RescueCase>> = flow {
        emit(Resource.Loading)
        try {
            val request = CreateRescueRequest(
                description = description,
                latitude = lat,
                longitude = lng,
                address = address,
                animal = AnimalDto(
                    species = animalSpecies,
                    breed = animalBreed,
                    sex = animalSex,
                    approximateAge = animalAge,
                    color = animalColor,
                    size = animalSize
                )
            )
            val response = api.reportRescue(request)
            if (response.isSuccessful && response.body() != null) {
                emit(Resource.Success(response.body()!!.toDomain()))
            } else {
                emit(Resource.Error(response.message() ?: "Failed to report rescue"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error occurred"))
        }
    }
}
