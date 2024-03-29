package com.example.app.injection

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppBindingModule::class, AppModule::class,
	ApiModule::class, DataBaseModule::class])
interface AppComponent : AndroidInjector<DaggerApplication> {

	override fun inject(app: DaggerApplication)

	@Component.Builder
	interface Builder {
		@BindsInstance
		fun application(application: Application): Builder

		fun build(): AppComponent
	}

}