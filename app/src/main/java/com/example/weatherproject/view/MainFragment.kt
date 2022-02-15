package com.example.weatherproject.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.weatherproject.viewmodel.AppState
import com.example.weatherproject.R
import com.example.weatherproject.databinding.MainFragmentBinding
import com.example.weatherproject.model.Weather
import com.example.weatherproject.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val observer = Observer<AppState> {
            renderData(it)
        }
        binding.buttonRefresh.setOnClickListener { viewModel.getWeatherFromLocal(); }
        viewModel.getLiveData().observe(viewLifecycleOwner, observer)
        viewModel.getWeatherFromLocal();
    }

    private fun renderData(appState: AppState) {
        Toast.makeText(context, "data", Toast.LENGTH_LONG).show()
        when (appState) {
            is AppState.Success -> {
                //при успехе обновляем отображение информации
                setWeatherValues(appState.weatherInfo);
                binding.loadingLayout.visibility = View.GONE
                Snackbar.make(binding.root, "Success", Snackbar.LENGTH_LONG).show()
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar
                    .make(binding.root, "Error", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload") { viewModel.getWeatherFromLocal() }
                    .show()
            }
        }
    }

    private fun setWeatherValues(weatherInfo: Weather) {
        binding.cityName.text = weatherInfo.city.city
        binding.cityCoordinates.text = String.format(
            getString(R.string.city_coordinates),
            weatherInfo.city.lat.toString(),
            weatherInfo.city.lon.toString()
        )
        binding.temperatureValue.text = weatherInfo.temperature.toString()
        binding.feelsLikeValue.text = weatherInfo.feelsLike.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}