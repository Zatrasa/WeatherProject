package com.example.weatherproject.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Weather(
    val city: City = getDefaultCity(),
    var temperature: Int = 0,
    var feelsLike: Int = 0,
    var condition: String? = "" ) : Parcelable {

    fun setWeather(temperature: Int, feelsLike: Int, condition: String) {
        this.temperature = temperature
        this.feelsLike = feelsLike
        this.condition = condition
    }
}
    fun getDefaultCity() = City("Москва",55.755826, 37.617299900000035)





