package com.example.mausam_weatherapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mausam_weatherapp.data.WeatherResponse
import com.example.mausam_weatherapp.data.WeatherRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel(private val weatherRepository: WeatherRepository):ViewModel() {
    private val _result = MutableLiveData<WeatherResponse>()
    val result:LiveData<WeatherResponse> get() = _result
    private val _error = MutableLiveData<String>()
    val error:LiveData<String> get() = _error

        fun getWeatherData(location: String) {
                val response = weatherRepository.getData(location)
            response.enqueue(object : Callback<WeatherResponse> {
                    override fun onResponse(
                        call: Call<WeatherResponse>,
                        response: Response<WeatherResponse>
                    ){
                        if (response.isSuccessful) {
                            val weatherResponse = response.body()
                            if (weatherResponse != null) {
                                _result.postValue(weatherResponse)
                            } else {
                                _error.postValue("Invalid response. Please try again later.")
                            }
                        }

                    }

                    override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                        _error.postValue(t.message)
                    }
                })
        }
}