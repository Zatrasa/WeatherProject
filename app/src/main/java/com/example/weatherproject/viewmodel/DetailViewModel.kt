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

    fun getWeatherFromRemote(){
        liveDataToObserve.postValue(AppState.Loading)
        try {
            val handler = Handler()
            val uri = URL("https://api.weather.yandex.ru/v2/forecast?lat=${weather.city.lat}&" +
                    "lon=${weather.city.lon}&lang=ru_RU")
            Thread {
                //Thread.sleep(1000)
                var urlConnection: HttpsURLConnection? = null
                try {
                    urlConnection = uri.openConnection() as HttpsURLConnection
                    urlConnection.requestMethod = "GET"
                    urlConnection.readTimeout = 10000
                    urlConnection.addRequestProperty("X-Yandex-API-Key","63803085-107f-42a1-abd0-0d3f30761e31")
                    val reader =
                        BufferedReader(InputStreamReader(urlConnection.inputStream))
                    val result = getLines(reader)
                    val weatherDTO : WeatherDTO =
                        Gson().fromJson(result, WeatherDTO::class.java)
                    weather.setWeather(weatherDTO.fact?.temp?:0,
                        weatherDTO.fact?.feels_like?:0,
                        weatherDTO.fact?.condition?:"")
                    liveDataToObserve.postValue(AppState.SuccessCity(this.weather))
                } catch (e: Exception) {
                    Log.e("weather123", "Fail connection", e)
                    e.printStackTrace()
                    liveDataToObserve.postValue(AppState.Error(e))
                } finally {
                    urlConnection?.disconnect()
                }
            }.start()
        } catch (e: MalformedURLException) {
            Log.e("", "Fail URI", e)
            e.printStackTrace()
            liveDataToObserve.postValue(AppState.Error(e))
        }
    }

    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

}