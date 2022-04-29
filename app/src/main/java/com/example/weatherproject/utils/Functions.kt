package com.example.weatherproject.utils

import com.example.weatherproject.model.City
import com.example.weatherproject.model.Weather
import com.example.weatherproject.room.HistoryEntity
import java.time.LocalDateTime

fun getCondition(cond : String):String =
    when(cond){
         "clear"-> "ясно"
         "partly-cloudy" -> "малооблачно"
         "cloudy" -> "облачно с прояснениями"
         "overcast" -> "пасмурно"
         "drizzle" -> "морось"
         "light-rain" -> "небольшой дождь"
         "rain" -> "дождь"
         "moderate-rain" -> "умеренно сильный дождь"
         "heavy-rain" -> "сильный дождь"
         "continuous-heavy-rain" -> "длительный сильный дождь"
         "showers" -> "ливень"
         "wet-snow" -> "дождь со снегом"
         "light-snow" -> "небольшой снег"
         "snow" -> "снег"
         "snow-showers" -> "снегопад"
         "hail" -> "град"
         "thunderstorm" -> "гроза"
         "thunderstorm-with-rain" -> "дождь с грозой"
         "thunderstorm-with-hail" -> "гроза с градом"
            else -> ""
    }

fun getConditionIconUrl(icon : String) =
     "https://yastatic.net/weather/i/icons/funky/dark/$icon.svg"

fun getGradus(temp: Int):String {
     return if (temp>0) {"+$temp\u00B0"}
     else {"$temp\u00B0"}
}

fun convertHistoryEntityToWeather(entityList: List<HistoryEntity>):
        List<Weather> {
     return entityList.map {
          Weather(City(it.city, 0.0, 0.0), it.temperature, 0, it.condition)
     }
}
fun convertWeatherToEntity(weather: Weather): HistoryEntity {
     return HistoryEntity(
          0, weather.city.city, weather.temperature,
          weather.condition
     )
}


