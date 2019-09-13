package com.example.app.data.entity

import com.google.gson.annotations.SerializedName

class Vehicle(
		@SerializedName("vehicleId")
		val vehicleId: Long,
		@SerializedName("vrn")
		val vrn: String,
		@SerializedName("country")
		val country: String,
		@SerializedName("color")
		val color: String,
		@SerializedName("type")
		val type: String,
		@SerializedName("default")
		val default: Boolean
)

