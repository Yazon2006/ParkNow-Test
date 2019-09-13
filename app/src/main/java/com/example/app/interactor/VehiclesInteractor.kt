package com.example.app.interactor

import com.example.app.data.VehiclesRepository
import com.example.app.data.entity.Vehicle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class VehiclesInteractor @Inject constructor(
		private val vehiclesRepository: VehiclesRepository) {

	fun observeVehicleUpdates(): Flow<List<Vehicle>> {
		return vehiclesRepository.vehicleUpdates
				.map { vehicles ->
					vehicles.sortedBy { it.vrn }
				}
	}

	suspend fun fetchVehicles() {
		//todo clarify pagination logic and add it
		val vehicles = vehiclesRepository.getVehicles()
		vehiclesRepository.saveVehiclesLocally(vehicles)
	}

	suspend fun getVehicleById(vehicleId: Long): Vehicle? {
		return vehiclesRepository.getVehicleById(vehicleId)
	}

}