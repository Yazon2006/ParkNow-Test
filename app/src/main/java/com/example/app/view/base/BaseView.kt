package com.example.app.view.base

import com.arellomobile.mvp.MvpView

interface BaseView : MvpView {
	fun showMessage(message: String)
}