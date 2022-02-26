package com.example.weatherproject.model

import kotlin.random.Random

//Класс в котором храняться все данные о погоде
//например полученные откуда-то или сгенерированные локально
class RepositoryImpl():Repository {
    private val wetherList_rus : List<Weather>
    private val wetherList_world : List<Weather>

    init {
        wetherList_rus = listOf(
            Weather(City("Саранск"),-1,-5),
            Weather(),
            Weather(City("Омск"),1,7),
            Weather(City("Воронеж"),7,10),
            Weather(City("Пермь"),-1,0),
            Weather(City("Астрахань"),12,15),
            Weather(City("Архангельск"),-5,-10),
            Weather(City("Брянск"),3,4)
        )
        wetherList_world = listOf(
            Weather(City("Гавана"),25,30),
            Weather(City("Каракас"),31,35),
            Weather(City("Мехико"),27,25),
        )

    }

//    override fun getWeatherFromServer(): Weather {
//        return Weather() //создаем и возвращаем клас с дефолтн. значениями
//    }

//    override fun getWeatherFromLocalStorage(): Weather {
//        //Возвращаем запрошенные данные, пока рандом
//        return wetherList.get(Random.nextInt(wetherList.size-1))
//    }

    override fun getWeatherFromLocalStorageAll(isRus : Boolean): List<Weather> {
        return if (isRus) wetherList_rus
        else wetherList_world
    }
}