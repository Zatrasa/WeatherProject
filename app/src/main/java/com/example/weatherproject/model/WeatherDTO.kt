package com.example.weatherproject.model

data class WeatherDTO(
    val fact : FactDTO?,
    val info : InfoDTO)

data class FactDTO(
    val temp: Int?,
    val feels_like: Int?,
    val condition: String?,
    val wind_speed: Double?,
    val icon : String?
)

data class InfoDTO(
    val lat : Double?,
    val lon : Double?,
    val tzinfo : TzinfoDTO?
)

data class TzinfoDTO(val name : String?)