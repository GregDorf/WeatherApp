package com.example.weatherapp.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weatherapp.viewmodel.WeatherViewModel
import com.example.weatherapp.ui.components.CityDropdown
import com.example.weatherapp.ui.components.WeatherForecast

@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {
    val selectedCity by viewModel.selectedCity
    val weather by viewModel.weatherState

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        CityDropdown(
            cities = viewModel.cityList,
            selectedCity = selectedCity,
            onCitySelected = viewModel::onCitySelected
        )

        Spacer(modifier = Modifier.height(16.dp))

        weather?.let {
            WeatherForecast(weatherResponse = it)
        } ?: Text("Выберите город для получения прогноза")
    }
}