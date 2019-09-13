package com.example.app.view.details

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.app.data.entity.Vehicle
import com.example.app.myapplication.R
import com.example.app.view.base.BaseFragment
import com.example.app.view.base.BaseView
import com.example.app.view.base.extensions.FragmentArg
import dagger.Lazy
import kotlinx.android.synthetic.main.fragment_vehicle_details.*
import javax.inject.Inject

@StateStrategyType(AddToEndSingleStrategy::class)
interface VehicleDetailsView : BaseView {
	fun showVehicleDetails(vehicle: Vehicle)
}

class VehicleDetailsFragment : BaseFragment(), VehicleDetailsView {
	override val layoutResId = R.layout.fragment_vehicle_details

	@Inject
	lateinit var daggerPresenter: Lazy<VehicleDetailsPresenter>

	@InjectPresenter
	lateinit var presenter: VehicleDetailsPresenter

	@ProvidePresenter
	fun providePresenter(): VehicleDetailsPresenter = daggerPresenter.get()

	var vehicleId by FragmentArg<Long>()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		presenter.vehicleId = vehicleId
	}

	override fun showVehicleDetails(vehicle: Vehicle) {
		vehicleVrnTextView.text = vehicle.vrn
		vehicleColorTextView.text = getString(R.string.color, vehicle.color)
		vehicleCountryTextView.text = getString(R.string.country, vehicle.country)
		vehicleTypeTextView.text = getString(R.string.type, vehicle.type)
		vehicleDefaultCheckBox.isChecked = vehicle.isDefault == true
	}
}