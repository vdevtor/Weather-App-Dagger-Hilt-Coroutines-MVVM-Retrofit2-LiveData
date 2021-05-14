package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.weatherResponse.observe(this, { weather ->
            binding.apply {
                tvTemperature.text = weather.temperature
                tvDescription.text = weather.description
                tvWind.text = weather.wind
                tvcity.text = "Sidney"

                val forecast1 = weather.forecast[0]
                val forecast2 = weather.forecast[1]
                val forecast3 = weather.forecast[2]

                tvForecast1.text = "${forecast1.temperature}/ ${forecast1.wind}"
                tvForecast2.text = "${forecast2.temperature}/ ${forecast2.wind}"
                tvForecast3.text = "${forecast3.temperature}/ ${forecast3.wind}"
            }
        })

        setSearchView()
    }

    fun setSearchView() {
        val searchView: SearchView? = binding.searchView

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.length >= 2) {
                    viewModel.setQuery(query)
                    viewModel.getWeather(viewModel.getQuery())
                    observableOnSearchResult(viewModel.getQuery())

                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

        })
    }

    fun observableOnSearchResult(query: String) {
        viewModel.weatherResponse.observe(this, { weather ->
            binding.apply {
                tvTemperature.text = weather.temperature
                tvDescription.text = weather.description
                tvWind.text = weather.wind
                tvcity.text = query
            }
        })
    }
}