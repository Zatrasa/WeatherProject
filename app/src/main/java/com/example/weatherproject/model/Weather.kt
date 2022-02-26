package com.example.weatherproject.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Weather(
    val city: City = getDefaultCity(),
    val temperature: Int = 0,
    val feelsLike: Int = 0) : Parcelable

    fun getDefaultCity():City{
        return City("Москва",55.755826, 37.617299900000035)
    }


