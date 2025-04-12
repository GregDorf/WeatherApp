package com.example.weatherapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weatherapp.model.WeatherResponse

@Composable
fun WeatherForecast(weatherResponse: WeatherResponse) {
    val current = weatherResponse.current_weather
    val isDayText = when (current.is_day) {
        1 -> "День"
        0 -> "Ночь"
        else -> "Неизвестно"
    }

    Column(modifier = Modifier.padding(8.dp)) {
        Text("Температура: ${current.temperature} °C", style = MaterialTheme.typography.titleMedium)
        Text("Скорость ветра: ${current.windspeed} км/ч")
        Text("Направление ветра: ${current.winddirection}°")
        Text("Код погоды: ${current.weathercode}")
        Text("Время обновления: ${current.time}")
        Text("Период обновления: ${current.interval ?: "н/д"} сек.")
        Text("Время суток: $isDayText")
    }
}
