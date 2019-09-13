package com.example.app.data

import com.example.app.data.api.ParkNowServiceApi
import com.example.app.data.entity.Vehicle
import io.objectbox.BoxStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VehiclesRepository @Inject constructor(
		private val boxStore: BoxStore,
		private val parkNowServiceApi: ParkNowServiceApi) {

	val vehicleUpdates = boxStore.boxFor(Vehicle::class.java)
			.query()
			.build()
			.toFlow()

	suspend fun getVehicles(): List<Vehicle> = withContext(Dispatchers.IO) {
		parkNowServiceApi.getVehicles().vehicles
	}

	suspend fun getVehicleById(vehicleId: Long): Vehicle? = withContext(Dispatchers.IO) {
		boxStore.boxFor(Vehicle::class.java)[vehicleId]
	}

	suspend fun saveVehiclesLocally(vehicles: List<Vehicle>) = withContext(Dispatchers.IO) {
		boxStore.boxFor(Vehicle::class.java).put(vehicles)
	}

}
