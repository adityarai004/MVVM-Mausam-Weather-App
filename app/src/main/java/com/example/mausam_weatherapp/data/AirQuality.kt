package com.example.mausam_weatherapp.data

import com.google.gson.annotations.SerializedName

data class AirQuality(
    val co: Double,
    @SerializedName("gb-defre-index")
    val gb_defra_index: Int,
    val no2: Double,
    val o3: Double,
    val pm10: Double,
    val pm2_5: Double,
    val so2: Double,
    @SerializedName("us-epa-index")
    val us_epa_index: Int
)