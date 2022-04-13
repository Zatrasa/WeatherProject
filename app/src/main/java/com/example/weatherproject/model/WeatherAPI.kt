package com.example.weatherproject.model

import com.example.weatherproject.utils.API_HEADER
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WeatherAPI {
    @GET("v2/informers")
    fun getWeather(
        @Header(API_HEADER) token: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Call<WeatherDTO>}