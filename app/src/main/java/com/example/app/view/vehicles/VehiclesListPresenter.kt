package com.example.app.view.vehicles

import com.arellomobile.mvp.InjectViewState
import com.example.app.interactor.VehiclesInteractor
import com.example.app.view.base.BasePresenter
import kotlinx.coroutines.launch
import javax.inject.Inject

@InjectViewState
class VehiclesListPresenter @Inject constructor(
		private val vehiclesInteractor: VehiclesInteractor
) : BasePresenter<VehiclesListView>() {

	override fun onFirstViewAttach() {
		super.onFirstViewAttach()
		launch {
			viewState.showVehiclesList(vehiclesInteractor.getVehicles())
		}
	}
}