package com.example.mausam_weatherapp.ui.models

data class ForecastItemModel(var day: String?    = null,
                             var precip: Double?    = null,
                             var icon: String? = null,
                             var high   : Double?    = null,
                             var low: Double? = null)