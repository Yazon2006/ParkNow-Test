package com.example.app.view.main

import com.arellomobile.mvp.InjectViewState
import com.example.app.view.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor() : BasePresenter<MainView>() {

	override fun onFirstViewAttach() {
		super.onFirstViewAttach()
		viewState.showVehiclesListFragment()
	}

}