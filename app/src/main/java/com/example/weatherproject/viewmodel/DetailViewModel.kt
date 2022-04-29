package com.example.weatherproject.viewmodel

import android.content.res.Resources
import android.os.Handler
import android.util.Log
import androidx.core.content.res.TypedArrayUtils.getString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherproject.R
import com.example.weatherproject.app.App.Companion.getHistoryDao
import com.example.weatherproject.model.*
import com.example.weatherproject.room.LocalRepository
import com.example.weatherproject.room.LocalRepositoryImpl
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class DetailViewModel :ViewModel(){

    val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    private val detailsRepositoryImpl: DetailsRepository =
        DetailsRepositoryImpl(RemoteDataSource())
    private val historyRepository: LocalRepository =
        LocalRepositoryImpl(getHistoryDao())

    private var weather : Weather = Weather()
    private val callBack = object :
        Callback<WeatherDTO> {
        override fun onResponse(call: Call<WeatherDTO>, response:
        Response<WeatherDTO>
        ) {val serverResponse: WeatherDTO? = response.body()
            liveDataToObserve.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    AppState.Error(Throwable("Ошибка сервера"))
                }
            )
        }

        override fun onFailure(call: Call<WeatherDTO>, t: Throwable) {
            liveDataToObserve.postValue(AppState.Error(Throwable(t.message ?:
            "Ошибка запроса на сервер")))
        }

        private fun checkResponse(serverResponse: WeatherDTO): AppState {
            val fact = serverResponse.fact
            return if (fact == null || fact.temp == null || fact.feels_like ==
                null || fact.condition.isNullOrEmpty()|| fact.icon.isNullOrEmpty()) {
                AppState.Error(Throwable("Неполные данные"))
            } else {
                weather.setWeather(fact?.temp?:0,
                    fact?.feels_like?:0,
                    fact?.condition?:"",fact?.icon?:"")
                Thread{saveCityToDB(weather)}.start()
                AppState.SuccessCity(weather)
            }
        }
    }

    fun saveCityToDB(weather: Weather) {
        historyRepository.saveEntity(weather)
    }


    fun loadWeather(weather:Weather){
        this.weather= weather
    }

    fun getWeatherFromRemote(){
        liveDataToObserve.postValue(AppState.Loading)
        detailsRepositoryImpl.getWeatherDetailsFromServer(weather.city.lat, weather.city.lon, callBack)
    }
}