package com.example.weatherproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherproject.model.Repository
import com.example.weatherproject.model.RepositoryImpl
import java.lang.Thread.sleep
import kotlin.random.Random

class MainViewModel(private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
                    private val repositoryImpl: Repository = RepositoryImpl()
) : ViewModel() {

    fun getLiveData() = liveDataToObserve  //возвращаем наблюдаемый объект

    fun getWeatherFromLocal()= getDataFromLocalSource()
    fun getWeatherFromRemote()= getDataFromLocalSource()

    //Запрос данных о погоде
    private fun getDataFromLocalSource() {
        //Перез запуском запроса меняем статус наблюдаемого объекта в "загружаем"
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(1000)
            //при успехе меняем статус наблюдаемого объекта в успех
            //и передаем туда полученные данные
            if (Random.nextBoolean())
              liveDataToObserve.postValue(AppState.Success(repositoryImpl.getWeatherFromLocalStorage()))
            else liveDataToObserve.postValue(AppState.Error(Throwable()))
        }.start()
    }
}