package com.example.weatherproject.model

interface Repository {
    //fun getWeatherFromServer(): Weather
    //fun getWeatherFromLocalStorage(): Weather
    fun getWeatherFromLocalStorageAll(isRus : Boolean): List<Weather>
}