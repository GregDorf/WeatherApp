package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.weatherapp.api.GeoApiService
import com.example.weatherapp.api.WeatherApiService
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.ui.theme.WeatherScreen
import com.example.weatherapp.viewmodel.WeatherViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {

    private val geoApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.api-ninjas.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GeoApiService::class.java)
    }

    private val weatherApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApiService::class.java)
    }

    private val ninjasApiKey = "HSJ5YsjFPXrttTeVtnHyjA==uKez54JWCyMTm1GD"

    private val repository by lazy {
        WeatherRepository(geoApi, weatherApi, ninjasApiKey)
    }

    private val viewModel by lazy {
        WeatherViewModel(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherScreen(viewModel = viewModel)
        }
    }
}
