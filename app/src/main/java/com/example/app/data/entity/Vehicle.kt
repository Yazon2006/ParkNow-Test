package com.example.app.data.entity

import com.google.gson.annotations.SerializedName
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
class Vehicle(
		@Id(assignable = true)
		@SerializedName("vehicleId")
		var id: Long = 0,
		@SerializedName("vrn")
		val vrn: String? = null,
		@SerializedName("country")
		val country: String? = null,
		@SerializedName("color")
		val color: String? = null,
		@SerializedName("type")
		val type: String? = null,
		@SerializedName("default")
		val isDefault: Boolean? = null
)

