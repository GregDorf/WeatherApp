package com.example.weatherapp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.weatherapp.viewmodel.WeatherViewModel // Убедитесь, что ваш ViewModel здесь доступен
import com.example.weatherapp.ui.components.CitySearchDropdown
import com.example.weatherapp.ui.components.WeatherForecast

@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {
    // Получаем состояние выбранного города и данных о погоде из ViewModel
    // Предполагается, что viewModel.selectedCity и viewModel.weatherState
    // являются Compose State (например, MutableState)
    val selectedCity by viewModel.selectedCity
    val weather by viewModel.weatherState

    // Примерный фон, который может меняться в зависимости от погоды или времени суток
    // В реальном приложении можно использовать более сложные градиенты или изображения
    val backgroundColor = when (weather?.current_weather?.is_day) {
        1 -> Color(0xFF87CEEB) // Голубое небо днем
        0 -> Color(0xFF003366) // Темно-синее небо ночью
        else -> Color(0xFFADD8E6) // Светло-голубой по умолчанию
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor) // Применяем фоновый цвет
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally // Выравниваем содержимое по центру по горизонтали
    ) {

        // Выпадающий список городов
        CitySearchDropdown(
            cities = viewModel.cityList,
            selectedCity = selectedCity,
            onCitySelected = viewModel::onCitySelected
        )

        Spacer(modifier = Modifier.height(24.dp)) // Увеличим отступ между списком и прогнозом

        // Отображение прогноза погоды или сообщения о выборе города
        if (weather != null) {
            // Если данные о погоде доступны, отображаем компонент WeatherForecast
            WeatherForecast(weatherResponse = weather!!)
        } else {
            // Если данных нет (город не выбран или загрузка), показываем сообщение
            // Можно добавить индикатор загрузки, если данные еще загружаются
            // CircularProgressIndicator()
            // Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Выберите город для получения прогноза...",
                color = Color.White.copy(alpha = 0.8f), // Сделаем текст немного прозрачным
                style = MaterialTheme.typography.titleMedium // Применим стиль текста
            )
        }

        // Здесь можно добавить место для прогноза на несколько дней или часов
        // Например, горизонтальный список карточек с прогнозом на ближайшие часы
        // Spacer(modifier = Modifier.height(16.dp))
        // LazyRow(...) { /* Карточки с прогнозом на часы */ }
    }
}
