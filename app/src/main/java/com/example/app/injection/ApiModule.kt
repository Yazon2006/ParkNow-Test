package com.example.app.injection

import com.example.app.data.api.ParkNowServiceApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class ApiModule {

	@Provides
	fun provideParkNowServiceApi(gson: Gson): ParkNowServiceApi {
		val httpClientBuilder = OkHttpClient.Builder()
				.readTimeout(60, TimeUnit.SECONDS)
				.connectTimeout(20, TimeUnit.SECONDS)
				.writeTimeout(60, TimeUnit.SECONDS)

		return Retrofit.Builder()
				.baseUrl("http://private-6d86b9-vehicles5.apiary-mock.com/")
				.addConverterFactory(GsonConverterFactory.create(gson))
				.client(httpClientBuilder.build())
				.build()
				.create(ParkNowServiceApi::class.java)
	}

	@Provides
	fun provideGson(): Gson {
		return GsonBuilder().create()
	}
}