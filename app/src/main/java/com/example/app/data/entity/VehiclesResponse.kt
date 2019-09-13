package com.example.app.data.entity

class VehiclesResponse(
		val count: Int,
		val vehicles: List<Vehicle>,
		val currentPage: Int,
		val nextPage: Int,
		val totalPages: Int
)