package com.example.weatherproject.room

import com.example.weatherproject.model.Weather
import com.example.weatherproject.utils.convertHistoryEntityToWeather
import com.example.weatherproject.utils.convertWeatherToEntity

class LocalRepositoryImpl(private val localDataSource: HistoryDao) : LocalRepository{



    override fun getAllHistory(): List<Weather> {
        return convertHistoryEntityToWeather(localDataSource.all())
    }

    override fun getSerchHistory(city : String): List<Weather> {
        return convertHistoryEntityToWeather(localDataSource.getDataByWord(city))
    }

    override fun saveEntity(weather: Weather) {
        localDataSource.insert(convertWeatherToEntity(weather))
    }
}