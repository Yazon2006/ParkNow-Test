package com.example.app.injection

import com.example.app.view.vehicles.VehiclesListFragment
import com.example.app.view.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppBindingModule {
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindVehiclesListFragment(): VehiclesListFragment
}