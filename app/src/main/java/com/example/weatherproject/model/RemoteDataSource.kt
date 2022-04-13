package com.example.weatherproject.model

import com.example.weatherproject.BuildConfig
import com.example.weatherproject.utils.API_KEY
import com.example.weatherproject.utils.BASE_URL
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource  {
    private val weatherApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        )
        .build().create(WeatherAPI::class.java)


    fun getWeatherDetails(lat: Double, lon: Double, callback:
    Callback<WeatherDTO>
    ) {
        weatherApi.getWeather(
            API_KEY, lat,
            lon).enqueue(callback)
    }
}
