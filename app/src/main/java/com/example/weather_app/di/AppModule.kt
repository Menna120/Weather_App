package com.example.weather_app.di

import android.app.Application
import com.example.weather_app.data.location.DefaultLocationTracker
import com.example.weather_app.data.remote.WeatherApi
import com.example.weather_app.data.repository.WeatherRepositoryImpl
import com.example.weather_app.domain.location.LocationTracker
import com.example.weather_app.domain.repository.WeatherRepository
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(
        api: WeatherApi
    ): WeatherRepository = WeatherRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideCurrentLocation(): CurrentLocationRequest {
        return CurrentLocationRequest.Builder().build()
    }

    @Provides
    @Singleton
    fun provideCancellationTokenSource(): CancellationTokenSource {
        return CancellationTokenSource()
    }

    @Provides
    @Singleton
    fun providesFusedLocationProviderClient(
        application: Application
    ): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)

    @Provides
    @Singleton
    fun providesLocationTracker(
        fusedLocationProviderClient: FusedLocationProviderClient,
        application: Application
    ): LocationTracker = DefaultLocationTracker(
        locationClient = fusedLocationProviderClient,
        currentLocationRequest = provideCurrentLocation(),
        cancellationTokenSource = provideCancellationTokenSource(),
        application = application
    )
}