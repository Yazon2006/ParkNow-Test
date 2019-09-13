package com.example.app

import com.example.app.data.VehiclesRepository
import com.example.app.data.entity.Vehicle
import com.example.app.interactor.VehiclesInteractor
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.runBlocking
import org.junit.Test

class VehiclesInteractorTest {

	@Test
	fun test() {
		runBlocking {
			val vehiclesRepository = mock<VehiclesRepository>()
			val vehiclesInteractor = VehiclesInteractor(vehiclesRepository)
			val vehicles = listOf<Vehicle>()
			whenever(vehiclesRepository.getVehicles()).thenReturn(vehicles)
			vehiclesInteractor.fetchVehicles()
			verify(vehiclesRepository, times(1).description("getVehicles() should be called only one in VehiclesInteractor fetchVehicles()")).getVehicles()
			verify(vehiclesRepository, atLeast(1).description("saveVehiclesLocally() should be called at least once in VehiclesInteractor fetchVehicles()")).saveVehiclesLocally(vehicles)
		}
	}
}