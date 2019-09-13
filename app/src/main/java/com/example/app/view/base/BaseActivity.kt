package com.example.app.view.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class BaseActivity : BaseView, MvpAppCompatActivity(), HasSupportFragmentInjector {

	abstract val layoutResId: Int

	@Inject
	lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

	override fun supportFragmentInjector(): AndroidInjector<Fragment> =
			fragmentDispatchingAndroidInjector

	override fun onCreate(savedInstanceState: Bundle?) {
		AndroidInjection.inject(this)
		super.onCreate(savedInstanceState)
		setContentView(layoutResId)
	}

	override fun showMessage(message: String) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
	}
}