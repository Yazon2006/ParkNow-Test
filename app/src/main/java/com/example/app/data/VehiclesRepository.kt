package com.example.app.data

import com.example.app.data.api.ParkNowServiceApi
import com.example.app.data.entity.Vehicle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VehiclesRepository @Inject constructor(
		private val parkNowServiceApi: ParkNowServiceApi) {

	suspend fun getVehicles(): List<Vehicle> = withContext(Dispatchers.IO) { parkNowServiceApi.getVehicles().vehicles }

}
