package com.example.app.injection

import android.content.Context
import com.example.app.data.entity.MyObjectBox
import dagger.Module
import dagger.Provides
import io.objectbox.BoxStore

@Module
class DataBaseModule {

	@Provides
	fun provideBoxStore(context: Context): BoxStore {
		return MyObjectBox.builder()
				.androidContext(context)
				.build()
	}

}