package com.example.app.view.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class BaseFragment : BaseView, MvpAppCompatFragment(), HasSupportFragmentInjector {

	open val navigationTag: String
		get() = this.javaClass.simpleName

	abstract val layoutResId: Int

	@Inject
	lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

	override fun supportFragmentInjector(): AndroidInjector<Fragment> = childFragmentInjector

	override fun onAttach(context: Context?) {
		AndroidSupportInjection.inject(this)
		super.onAttach(context)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, state: Bundle?): View {
		return inflater.inflate(layoutResId, container, false)
	}

	override fun showMessage(message: String) {
		(activity as? BaseView)?.showMessage(message)
	}
}