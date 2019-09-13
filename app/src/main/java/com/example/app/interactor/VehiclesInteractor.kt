package com.example.app.interactor

import com.example.app.data.VehiclesRepository
import com.example.app.data.entity.Vehicle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VehiclesInteractor @Inject constructor(
		private val vehiclesRepository: VehiclesRepository) {

	suspend fun getVehicles(): List<Vehicle> = withContext(Dispatchers.IO) { vehiclesRepository.getVehicles() }

}