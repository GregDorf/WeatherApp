package com.example.weatherapp.repository

import com.example.weatherapp.api.GeoApiService
import com.example.weatherapp.api.WeatherApiService
import com.example.weatherapp.model.CityResponse
import com.example.weatherapp.model.WeatherResponse

class WeatherRepository(
    private val geoApi: GeoApiService,
    private val weatherApi: WeatherApiService,
    private val ninjasApiKey: String
) {
    suspend fun getCoordinates(city: String): CityResponse? {
        return geoApi.getCityCoordinates(city, ninjasApiKey).firstOrNull()
    }

    suspend fun getWeather(city: String): WeatherResponse? {
        val cityData = getCoordinates(city)
        return cityData?.let {
            weatherApi.getWeatherForecast(it.latitude, it.longitude)
        }
    }
}
