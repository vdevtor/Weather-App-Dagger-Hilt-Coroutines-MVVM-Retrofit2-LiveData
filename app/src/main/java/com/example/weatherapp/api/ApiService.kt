package com.example.weatherapp.api

import com.example.weatherapp.model.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("weather/{city}")
    suspend fun getWeather(@Path("city") query: String): Response<Weather>
}