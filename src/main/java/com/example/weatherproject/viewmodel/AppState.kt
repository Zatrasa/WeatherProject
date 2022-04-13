package com.example.weatherproject.viewmodel

import com.example.weatherproject.model.Weather

//Наблюдаемый объект
//Объект за состоянием которого следит viewmodel
sealed class AppState{
    data class Success(val weatherList: List<Weather>): AppState()
    data class SuccessCity(val weather: Weather): AppState()
    object Loading: AppState() //Состояние грузим данные
    data class Error(val error : String): AppState() //Состояние ошибка, в котором нужно будет хранить описание ошибки
}
