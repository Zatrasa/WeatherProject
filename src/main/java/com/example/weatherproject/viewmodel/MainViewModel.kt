package com.example.weatherproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherproject.model.Repository
import com.example.weatherproject.model.RepositoryImpl
import java.lang.Thread.sleep
import kotlin.random.Random

class MainViewModel(val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
                    private val repositoryImpl: Repository = RepositoryImpl()
) : ViewModel() {

    fun getLiveData() = liveDataToObserve  //возвращаем наблюдаемый объект

    fun getWeatherFromLocal(isRus : Boolean)= getDataFromLocalSource(isRus)

    //Запрос данных о погоде
    private fun getDataFromLocalSource(isRus : Boolean) {
        liveDataToObserve.postValue(AppState.Loading)
        Thread {
            sleep(1000)
            liveDataToObserve.postValue(AppState.Success(repositoryImpl.getWeatherFromLocalStorageAll(isRus)))
        }.start()
    }
}