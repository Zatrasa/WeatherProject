package com.example.weatherproject.viewmodel

import android.content.res.Resources
import android.os.Handler
import android.util.Log
import androidx.core.content.res.TypedArrayUtils.getString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherproject.R
import com.example.weatherproject.model.City
import com.example.weatherproject.model.Weather
import com.example.weatherproject.model.WeatherDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection





class DetailViewModel :ViewModel(){
    val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    private var weather : Weather = Weather()

    fun loadWeather(weather:Weather){
        this.weather= weather
    }

    fun getWeatherFromModel()=this.weather


    fun setLoadWeather(weather: Weather){
        this.weather.setWeather(weather.temperature,weather.feelsLike,weather.condition?:"")
        liveDataToObserve.postValue(AppState.SuccessCity(this.weather))
    }

    fun setLoading(){
        liveDataToObserve.postValue(AppState.Loading)
    }

    fun setError(str : String) = liveDataToObserve.postValue(AppState.Error(str))

    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

}