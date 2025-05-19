package com.example.weatherapp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.weatherapp.viewmodel.WeatherViewModel
import com.example.weatherapp.ui.components.CitySearchDropdown
import com.example.weatherapp.ui.components.WeatherForecast

@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {
    val selectedCity by viewModel.selectedCity
    val weather by viewModel.weatherState

    val backgroundColor = when (weather?.current_weather?.is_day) {
        1 -> Color(0xFF87CEEB)
        0 -> Color(0xFF003366)
        else -> Color(0xFFADD8E6)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CitySearchDropdown(
            cities = viewModel.cityList,
            selectedCity = selectedCity,
            onCitySelected = viewModel::onCitySelected
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (weather != null) {
            WeatherForecast(weatherResponse = weather!!)
        } else {
            Text(
                "Выберите город для получения прогноза...",
                color = Color.White.copy(alpha = 0.8f),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
