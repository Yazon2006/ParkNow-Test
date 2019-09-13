package com.example.app.view.base

import com.arellomobile.mvp.MvpPresenter
import com.example.app.myapplication.BuildConfig
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

abstract class BasePresenter<TPresenterView : BaseView> : MvpPresenter<TPresenterView>(), CoroutineScope {

	override val coroutineContext: CoroutineContext
		get() = Dispatchers.Main + SupervisorJob() + CoroutineExceptionHandler { _, throwable ->
			handleError(throwable)
		}

	protected fun handleError(throwable: Throwable) {
		if (BuildConfig.DEBUG) throwable.printStackTrace()
		throwable.message?.let { message ->
			if (message.isNotBlank()) {
				viewState.showMessage(message)
			}
		}
	}

}