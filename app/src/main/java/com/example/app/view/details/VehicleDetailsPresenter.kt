package com.example.app.view.details

import com.arellomobile.mvp.InjectViewState
import com.example.app.interactor.VehiclesInteractor
import com.example.app.view.base.BasePresenter
import kotlinx.coroutines.launch
import javax.inject.Inject

@InjectViewState
class VehicleDetailsPresenter @Inject constructor(
		private val vehiclesInteractor: VehiclesInteractor
) : BasePresenter<VehicleDetailsView>() {

	var vehicleId: Long = 0L

	override fun onFirstViewAttach() {
		super.onFirstViewAttach()
		launch {
			val vehicle = vehiclesInteractor.getVehicleById(vehicleId)
			if (vehicle != null) {
				viewState.showVehicleDetails(vehicle)
			}
		}
	}

}