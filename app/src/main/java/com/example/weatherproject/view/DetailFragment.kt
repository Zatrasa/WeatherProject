package com.example.weatherproject.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.weatherproject.R
import com.example.weatherproject.databinding.DetailFragmentBinding
import com.example.weatherproject.model.Weather
import com.example.weatherproject.viewmodel.AppState
import com.example.weatherproject.viewmodel.DetailViewModel
import com.google.android.material.snackbar.Snackbar


class DetailFragment : Fragment() {

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DetailViewModel

    companion object {
        const val BUNDLE_EXTRA = "weather"

        fun newInstance(bundle: Bundle? = null) = DetailFragment().apply { this.arguments = bundle }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.liveDataToObserve.observe(viewLifecycleOwner, Observer {
            renderData(it)
        })
        arguments?.getParcelable<Weather>(BUNDLE_EXTRA)?.let {
            viewModel.loadWeather(it)
            viewModel.getWeatherFromRemote()
        }
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.SuccessCity -> {
                with(binding) {
                    weatherCondition.text = appState.weather.condition.toString()
                    temperatureValue.text = appState.weather.temperature.toString()
                    feelsLikeValue.text = appState.weather.feelsLike.toString()
                    cityName.text = appState.weather.city.city.toString()
                    cityCoordinates.text = String.format(
                        getString(R.string.city_coordinates),
                        appState.weather.city.lat.toString(),
                        appState.weather.city.lon.toString()
                    )
                    detailFragmentLoadingLayout.visibility = View.GONE
                }
            }
            is AppState.Loading -> {
                binding.detailFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.detailFragmentLoadingLayout.visibility = View.GONE
                Snackbar
                    .make(binding.root, "Error", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload") { viewModel.getWeatherFromRemote()}
                    .show()
            }
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }



}