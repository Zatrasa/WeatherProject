package com.example.weatherproject.viewmodel

import com.example.weatherproject.model.Weather

//Наблюдаемый объект
//Объект за состоянием которого следит viewmodel
sealed class AppState{
    data class Success(val weatherInfo: Weather): AppState() //при успехе возвращаем погоду
    object Loading: AppState() //Состояние грузим данные
    data class Error(val error : Throwable): AppState() //Состояние ошибка, в котором нужно будет хранить описание ошибки
}
