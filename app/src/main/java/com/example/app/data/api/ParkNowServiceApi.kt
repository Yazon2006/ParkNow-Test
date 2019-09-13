package com.example.app.data.api

import com.example.app.data.entity.VehiclesResponse
import retrofit2.http.GET

interface ParkNowServiceApi {
	@GET("vehicles")
	suspend fun getVehicles(): VehiclesResponse
}