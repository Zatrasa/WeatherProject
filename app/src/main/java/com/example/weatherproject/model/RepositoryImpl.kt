package com.example.weatherproject.model

import kotlin.random.Random

//Класс в котором храняться все данные о погоде
//например полученные откуда-то или сгенерированные локально
class RepositoryImpl():Repository {
    private val wetherList : ArrayList<Weather>

    init {
        wetherList = ArrayList()
        wetherList.add(Weather(City("Саранск"),-1,-5))
        wetherList.add(Weather())
        wetherList.add(Weather(City("Омск"),1,7))
        wetherList.add(Weather(City("Воронеж"),7,10))
        wetherList.add(Weather(City("Пермь"),-1,0))
        wetherList.add(Weather(City("Астрахань"),12,15))
        wetherList.add(Weather(City("Архангельск"),-5,-10))
        wetherList.add(Weather(City("Брянск"),3,4))
    }

    override fun getWeatherFromServer(): Weather {
        return Weather() //создаем и возвращаем клас с дефолтн. значениями
    }

    override fun getWeatherFromLocalStorage(): Weather {
        //Возвращаем запрошенные данные, пока рандом
        return wetherList.get(Random.nextInt(wetherList.size-1))
    }
}