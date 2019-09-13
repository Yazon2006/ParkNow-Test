package com.example.app.view.vehicles

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.app.data.entity.Vehicle
import com.example.app.myapplication.R
import com.example.app.view.base.BaseFragment
import com.example.app.view.base.BaseView
import dagger.Lazy
import javax.inject.Inject

@StateStrategyType(AddToEndSingleStrategy::class)
interface VehiclesListView : BaseView {

	fun showVehiclesList(vehicles: List<Vehicle>)
}

class VehiclesListFragment : BaseFragment(), VehiclesListView {

	override val layoutResId = R.layout.fragment_vehicles_list
	@Inject
	lateinit var daggerPresenter: Lazy<VehiclesListPresenter>

	@InjectPresenter
	lateinit var presenter: VehiclesListPresenter

	@ProvidePresenter
	fun providePresenter(): VehiclesListPresenter = daggerPresenter.get()

	override fun showVehiclesList(vehicles: List<Vehicle>) {
		showMessage(vehicles.toString())
	}

}