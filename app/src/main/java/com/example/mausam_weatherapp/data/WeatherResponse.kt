package com.example.mausam_weatherapp.data

data class WeatherResponse(
    val alerts: Alerts,
    val current: Current,
    val forecast: Forecast,
    val location: Location
)