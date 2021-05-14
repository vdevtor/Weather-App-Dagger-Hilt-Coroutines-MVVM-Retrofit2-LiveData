package com.example.weatherapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.Weather
import com.example.weatherapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel
@Inject
constructor(private val repository: WeatherRepository) : ViewModel() {
    private val _resp = MutableLiveData<Weather>()
    private var query = ""
    val weatherResponse: LiveData<Weather>
        get() = _resp

    init {
        getWeather("Saopaulo")
    }

    fun getWeather(query: String) = viewModelScope.launch {
        repository.getWeather(query).let { response ->
            if (response.isSuccessful) {
                _resp.postValue(response.body())
            } else {
                Log.d("Tag", "getWeather Error Response : ${response.message()}")
            }
        }
    }

    fun setQuery(newQuery: String) {
        newQuery.replace(" ", "")
        this.query = newQuery
    }

    fun getQuery(): String {
        return query
    }
}