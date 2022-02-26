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
import com.example.weatherproject.databinding.DetailFragmentBinding
import com.example.weatherproject.model.Weather
import com.example.weatherproject.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class DetailFragment : Fragment() {

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel

    companion object {

        const val BUNDLE_EXTRA = "weather"

        fun newInstance(bundle: Bundle? = null): DetailFragment {
            val fragment = DetailFragment()
            fragment.arguments = bundle
            return fragment
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val weather = arguments?.getParcelable<Weather>(BUNDLE_EXTRA)
        if (weather != null) {
            binding.cityName.text = weather.city.city
            binding.cityCoordinates.text = String.format(
                getString(R.string.city_coordinates),
                weather.city.lat.toString(),
                weather.city.lon.toString()
            )
            binding.temperatureValue.text = weather.temperature.toString()
            binding.feelsLikeValue.text = weather.feelsLike.toString()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        //val view = binding.root
        return binding.root
    }



    //
//    private fun setWeatherValues(weatherInfo: Weather) {
//        binding.cityName.text = weatherInfo.city.city
//        binding.cityCoordinates.text = String.format(
//            getString(R.string.city_coordinates),
//            weatherInfo.city.lat.toString(),
//            weatherInfo.city.lon.toString()
//        )
//        binding.temperatureValue.text = weatherInfo.temperature.toString()
//        binding.feelsLikeValue.text = weatherInfo.feelsLike.toString()
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}