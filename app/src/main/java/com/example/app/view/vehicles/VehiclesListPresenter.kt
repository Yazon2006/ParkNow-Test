package com.example.app.view.vehicles

import com.arellomobile.mvp.InjectViewState
import com.example.app.interactor.VehiclesInteractor
import com.example.app.view.base.BasePresenter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@InjectViewState
class VehiclesListPresenter @Inject constructor(
		private val vehiclesInteractor: VehiclesInteractor
) : BasePresenter<VehiclesListView>() {

	override fun onFirstViewAttach() {
		super.onFirstViewAttach()
		//launch observe any vehicle updates
		launch {
			vehiclesInteractor.observeVehicleUpdates()
					.collect {
						viewState.showVehiclesList(it)
					}
		}
		fetchInfo()
	}

	fun fetchInfo() {
		launch {
			try {
				vehiclesInteractor.fetchVehicles()
			} catch (e: Exception) {
				viewState.showErrorRetrieveFromServer()
			}
		}
	}
}