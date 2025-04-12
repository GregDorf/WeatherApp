package com.example.weatherapp.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.WeatherResponse
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.repository._cityList
import com.example.weatherapp.repository.cityNameMap
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    private val _weatherState = mutableStateOf<WeatherResponse?>(null)
    val weatherState: State<WeatherResponse?> = _weatherState

    private val _selectedCity = mutableStateOf(_cityList.first())
    val selectedCity: State<String> = _selectedCity

    val cityList = _cityList

    fun onCitySelected(city: String) {
        _selectedCity.value = city
        val englishName = cityNameMap[city] ?: city
        println("Выбран город: $city (отправляем: $englishName)")
        fetchWeather(englishName)
    }

    fun fetchWeather(englishCity: String) {
        viewModelScope.launch {
            val weather = repository.getWeather(englishCity)
            if (weather == null) {
                println("Не удалось получить прогноз для $englishCity")
            } else {
                println("Прогноз получен: $weather")
            }
            _weatherState.value = weather
        }
    }
}
