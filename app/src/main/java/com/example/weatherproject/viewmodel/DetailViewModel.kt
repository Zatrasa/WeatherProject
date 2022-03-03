package com.example.weatherproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherproject.model.Weather

class DetailViewModel :ViewModel(){
    private val _weather : MutableLiveData<Weather> = MutableLiveData()
    val weather : LiveData<Weather> get() = _weather

    fun setWeather(weather:Weather){
        _weather.value = weather
    }

}