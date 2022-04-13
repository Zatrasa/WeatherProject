package com.example.weatherproject.model

interface Repository {
    fun getWeatherFromLocalStorageAll(isRus : Boolean): List<Weather>
}