package com.example.weatherproject.room

import com.example.weatherproject.model.Weather

interface LocalRepository {
    fun getAllHistory(): List<Weather>
    fun saveEntity(weather: Weather)
    fun getSerchHistory(city : String): List<Weather>
}