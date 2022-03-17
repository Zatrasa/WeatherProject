package com.example.weatherproject.model

import kotlin.random.Random

//Класс в котором храняться все данные о погоде
//например полученные откуда-то или сгенерированные локально
class RepositoryImpl():Repository {
    private val wetherList_rus : List<Weather>
    private val wetherList_world : List<Weather>

    init {
        wetherList_rus = listOf(
            Weather(City("Саранск",54.1838, 45.1749),-1,-5),
            Weather(),
            Weather(City("Омск",54.9924, 73.3686),1,7),
            Weather(City("Воронеж",51.672, 39.1843),7,10),
            Weather(City("Пермь",58.0105, 56.2502),-1,0),
            Weather(City("Астрахань",46.3497, 48.0408),12,15),
            Weather(City("Архангельск",64.5401, 40.5433),-5,-10),
            Weather(City("Брянск",53.2521, 34.3717),3,4),
            Weather(City("Иркутск",52.2978, 104.296),-5,-3)
        )
        wetherList_world = listOf(
            Weather(City("Гавана",23.133,-82.383),25,30),
            Weather(City("Каракас",10.488,-66.8792),31,35),
            Weather(City("Мехико",19.43261, -99.13321),27,25),
        )
    }

    override fun getWeatherFromLocalStorageAll(isRus : Boolean)=
        if (isRus) wetherList_rus
        else wetherList_world

}