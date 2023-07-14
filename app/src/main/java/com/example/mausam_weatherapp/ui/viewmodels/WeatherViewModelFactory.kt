package com.example.mausam_weatherapp.ui.viewmodels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mausam_weatherapp.data.WeatherRepository

@Suppress("UNCHECKED_CAST")
class WeatherViewModelFactory constructor(private val repository: WeatherRepository):
    ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            WeatherViewModel(this.repository) as T
        }
        else{
            throw IllegalArgumentException("View Model Not Found")
        }
    }
}