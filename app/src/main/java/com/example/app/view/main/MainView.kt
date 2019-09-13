package com.example.app.view.main

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.app.myapplication.R
import com.example.app.view.base.BaseActivity
import com.example.app.view.base.BaseView
import com.example.app.view.vehicles.VehiclesListFragment
import dagger.Lazy
import javax.inject.Inject

interface MainView : BaseView {
	@StateStrategyType(OneExecutionStateStrategy::class)
	fun showVehiclesListFragment()
}

class MainActivity : BaseActivity(), MainView {

	override val layoutResId = R.layout.activity_main

	@Inject
	lateinit var daggerPresenter: Lazy<MainPresenter>

	@InjectPresenter
	lateinit var presenter: MainPresenter

	@ProvidePresenter
	fun providePresenter(): MainPresenter = daggerPresenter.get()

	override fun showVehiclesListFragment() {
		supportFragmentManager.beginTransaction().add(R.id.frameLayout, VehiclesListFragment())
				.commit()
	}

}
