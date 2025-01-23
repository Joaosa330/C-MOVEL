package com.example.weatherapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.LiveData
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.example.weatherapp.WeatherViewModel

@Composable
fun WeatherScreen(viewModel: WeatherViewModel, city: String, apiKey: String) {
    val weatherState by viewModel.weatherState.observeAsState(
        WeatherViewModel.WeatherState.Loading
    )
    LaunchedEffect(key1 = city) {
        viewModel.getWeather(city, "721d7638793f057d376a24399df55ec4") // Replace with your actual API key
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (weatherState) {
            is WeatherViewModel.WeatherState.Loading -> {
                Text("Loading...")
            }
            is WeatherViewModel.WeatherState.Success -> {
                Text(text = "Temperature: ${(weatherState as WeatherViewModel.WeatherState.Success).temperature}°C")
                Text(text = "Description: ${(weatherState as WeatherViewModel.WeatherState.Success).description}")
            }
            is WeatherViewModel.WeatherState.Error -> {
                Text(text = "Error: ${(weatherState as WeatherViewModel.WeatherState.Error).message}")
            }
        }
    }
    LaunchedEffect(key1 = city) {
        viewModel.getWeather(city, apiKey)
    }
}