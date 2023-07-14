package com.example.mausam_weatherapp.api

import com.example.mausam_weatherapp.data.WeatherResponse
import com.example.mausam_weatherapp.utils.Constants.Companion.API_KEY
import com.example.mausam_weatherapp.utils.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("v1/forecast.json")
    fun getWeatherForecast(
        @Query("q")
        loc:String,
        @Query("days")
        days:Int = 3,
        @Query("aqi")
        aqi:String = "yes",
        @Query("alerts")
        alerts:String = "yes",
        @Query("key")
        apiKey:String = API_KEY
    ): Call<WeatherResponse>


    companion object{
        private val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        private val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .method(original.method, original.body)
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .addInterceptor(loggingInterceptor)
            .build()
        var weatherService: WeatherAPI? = null
        fun createRetrofitInstance(): WeatherAPI? {
            if(weatherService == null) {
                val retrofit =  Retrofit.Builder()
                    .client(client)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                weatherService = retrofit.create(WeatherAPI::class.java)
            }
            return weatherService!!
        }
    }
}