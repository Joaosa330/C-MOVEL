package com.example.weatherapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.WeatherService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.UnknownHostException

class WeatherViewModel : ViewModel() {

    private val _weatherState = MutableLiveData<WeatherState>()
    val weatherState: LiveData<WeatherState> = _weatherState

    private val weatherService: WeatherService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        weatherService = retrofit.create(WeatherService::class.java)
    }

    fun getWeather(city: String, apiKey: String) {
        viewModelScope.launch {
            _weatherState.value = WeatherState.Loading
            try {
                val response = weatherService.getWeather(city, apiKey)
                _weatherState.value = WeatherState.Success(
                    temperature = response.main.temp,
                    description = response.weather.firstOrNull()?.description ?: ""
                )
            }
            catch (e: IOException) {
                _weatherState.value = WeatherState.Error("Network Error: ${e.message ?: "An unknown error occurred."}")             } catch (e: UnknownHostException) {
            }
        }
    }
    public sealed class WeatherState {
        data object Loading: WeatherState()
        data class Success(val temperature: Double, val description: String) : WeatherState()
        data class Error(val message: String) : WeatherState()
    }
}