package com.example.mausam_weatherapp.data

import com.example.mausam_weatherapp.api.WeatherAPI

class WeatherRepository(private val retroService: WeatherAPI) {
    fun getData(location:String) = retroService.getWeatherForecast(location)
}