package com.example.weatherapp.api

import com.example.weatherapp.model.CityResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GeoApiService {
    @GET("v1/city")
    suspend fun getCityCoordinates(
        @Query("name") cityName: String,
        @Header("X-Api-Key") apiKey: String
    ): List<CityResponse>
}
