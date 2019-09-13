package com.example.app.view.vehicles

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.app.data.entity.Vehicle
import com.example.app.myapplication.R
import com.example.app.view.base.BaseFragment
import com.example.app.view.base.BaseView
import com.example.app.view.base.extensions.FragmentAction
import com.example.app.view.base.extensions.MiddleFragmentSlideAnimation
import com.example.app.view.base.extensions.isVisible
import com.example.app.view.base.extensions.switchFragments
import com.example.app.view.details.VehicleDetailsFragment
import dagger.Lazy
import kotlinx.android.synthetic.main.fragment_vehicles_list.*
import javax.inject.Inject

@StateStrategyType(AddToEndSingleStrategy::class)
interface VehiclesListView : BaseView {

	fun showVehiclesList(vehicles: List<Vehicle>)

	@StateStrategyType(OneExecutionStateStrategy::class)
	fun showErrorRetrieveFromServer()
}

class VehiclesListFragment : BaseFragment(), VehiclesListView {

	override val layoutResId = R.layout.fragment_vehicles_list
	@Inject
	lateinit var daggerPresenter: Lazy<VehiclesListPresenter>

	@InjectPresenter
	lateinit var presenter: VehiclesListPresenter

	@ProvidePresenter
	fun providePresenter(): VehiclesListPresenter = daggerPresenter.get()

	@Inject
	lateinit var vehiclesAdapter: VehiclesAdapter

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		vehiclesRecyclerView.adapter = vehiclesAdapter
		swipeRefreshLayout.setOnRefreshListener { presenter.fetchInfo() }
		vehiclesAdapter.onVehicleClick = { vehicle -> showVehicleDetails(vehicle.id) }
		swipeRefreshLayout.isRefreshing = true
	}

	override fun showVehiclesList(vehicles: List<Vehicle>) {
		swipeRefreshLayout.isRefreshing = false
		emptyPlaceHolderLayout.isVisible = vehicles.isEmpty()
		vehiclesRecyclerView.isVisible = vehicles.isNotEmpty()
		vehiclesAdapter.vehicles = vehicles
	}

	override fun showErrorRetrieveFromServer() {
		Toast.makeText(requireContext(), getString(R.string.retry_vehicle_list), Toast.LENGTH_LONG).show()
	}

	private fun showVehicleDetails(vehicleId: Long) {
		val vehiclesDetailsFragment = VehicleDetailsFragment()
		vehiclesDetailsFragment.vehicleId = vehicleId
		activity?.switchFragments(FragmentAction(
				containerResId = R.id.frameLayout,
				fragment = vehiclesDetailsFragment,
				animation = MiddleFragmentSlideAnimation,
				addToBackStack = true
		))
	}
}
