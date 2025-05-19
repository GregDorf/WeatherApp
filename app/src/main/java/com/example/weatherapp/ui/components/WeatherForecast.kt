package com.example.weatherapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weatherapp.R
import com.example.weatherapp.model.WeatherResponse

@Composable
fun WeatherForecast(weatherResponse: WeatherResponse) {
    val current = weatherResponse.current_weather
    val isDayText = when (current.is_day) {
        1 -> "День"
        0 -> "Ночь"
        else -> "Неизвестно"
    }

    fun getWeatherIconUrl(weatherCode: Int, isDay: Int): String {
        val baseUrl = "https://weather-sense.leftium.com/icons/airy/"

        val baseIconName = when (weatherCode) {
            0 -> "clear"
            1 -> "mostly-clear"
            2 -> "partly-cloudy"
            3 -> "overcast"
            45 -> "fog"
            48 -> "rime-fog"
            51 -> "light-drizzle"
            53 -> "moderate-drizzle"
            55 -> "dense-drizzle"
            56 -> "light-freezing-drizzle"
            57 -> "dense-freezing-drizzle"
            61 -> "light-rain"
            63 -> "moderate-rain"
            65 -> "heavy-rain"
            66 -> "light-freezing-rain"
            67 -> "heavy-freezing-rain"
            71 -> "slight-snowfall"
            73 -> "moderate-snowfall"
            75 -> "heavy-snowfall"
            77 -> "snowflake"
            80 -> "light-rain"
            81 -> "moderate-rain"
            82 -> "heavy-rain"
            85 -> "slight-snowfall"
            86 -> "heavy-snowfall"
            95 -> "thunderstorm"
            96, 99 -> "thunderstorm-with-hail"
            else -> "unknown"
        }

        // Просто имя + @4x.png без -day/-night
        val fileName = "$baseIconName@4x.png"
        return baseUrl + fileName
    }


    val weatherIconUrl = getWeatherIconUrl(current.weathercode, current.is_day ?: 1)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = weatherIconUrl,
                contentDescription = "Иконка погоды",
                modifier = Modifier.size(100.dp),
                placeholder = painterResource(R.drawable.ic_launcher_foreground),
                error = painterResource(R.drawable.error)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "${current.temperature}°C",
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 70.sp
            )

            //Text(
            //    "Состояние погоды (код ${current.weathercode})",
            //    style = MaterialTheme.typography.titleMedium,
            //    color = MaterialTheme.colorScheme.onSurfaceVariant
            //)

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Ветер", style = MaterialTheme.typography.labelMedium)
                    Text("${current.windspeed} км/ч", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Напр.", style = MaterialTheme.typography.labelMedium)
                    Text("${current.winddirection}°", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Время суток", style = MaterialTheme.typography.labelMedium)
                    Text(isDayText, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Обновлено: ${current.time}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
        }
    }
}
