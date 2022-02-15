package com.example.weatherproject.model

data class Weather(
    val city: City = getDefaultCity(),
    val temperature: Int = 0,
    val feelsLike: Int = 0)

    fun getDefaultCity():City{
        return City("Москва",55.755826, 37.617299900000035)
    }

