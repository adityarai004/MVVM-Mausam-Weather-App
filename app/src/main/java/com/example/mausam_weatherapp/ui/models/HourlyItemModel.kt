package com.example.mausam_weatherapp.ui.models

data class HourlyItemModel(var time: String?    = null,
                           var tempC: Double?    = null,
                           var icon: String? = null,
                           var feelslikeC   : Double?    = null)