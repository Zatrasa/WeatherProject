package com.example.weatherproject.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.weatherproject.*
import com.example.weatherproject.databinding.DetailFragmentBinding
import com.example.weatherproject.model.Weather
import com.example.weatherproject.viewmodel.*
import com.google.android.material.snackbar.Snackbar


class DetailFragment : Fragment() {

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DetailViewModel
    private val loadResultsReceiver: BroadcastReceiver = object :
        BroadcastReceiver(){
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.getStringExtra(DETAILS_LOAD_RESULT_EXTRA)) {
                DETAILS_INTENT_EMPTY_EXTRA -> viewModel.setError(DETAILS_INTENT_EMPTY_EXTRA)
                DETAILS_DATA_EMPTY_EXTRA -> viewModel.setError(DETAILS_DATA_EMPTY_EXTRA)
                DETAILS_RESPONSE_EMPTY_EXTRA -> viewModel.setError(DETAILS_RESPONSE_EMPTY_EXTRA)
                DETAILS_REQUEST_ERROR_EXTRA -> viewModel.setError(DETAILS_REQUEST_ERROR_EXTRA)
                DETAILS_REQUEST_ERROR_MESSAGE_EXTRA -> viewModel.setError(DETAILS_REQUEST_ERROR_MESSAGE_EXTRA)
                DETAILS_URL_MALFORMED_EXTRA -> viewModel.setError(DETAILS_URL_MALFORMED_EXTRA)
                DETAILS_RESPONSE_SUCCESS_EXTRA -> viewModel.setLoadWeather(
                    Weather(temperature = intent.getIntExtra(DETAILS_TEMP_EXTRA, TEMP_INVALID),
                        feelsLike = intent.getIntExtra(DETAILS_FEELS_LIKE_EXTRA, FEELS_LIKE_INVALID),
                        condition = intent.getStringExtra(DETAILS_CONDITION_EXTRA)))
                else -> viewModel.setError(PROCESS_ERROR)
            }
        }

    }



    companion object {
        const val BUNDLE_EXTRA = "weather"

        fun newInstance(bundle: Bundle? = null) = DetailFragment().apply { this.arguments = bundle }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            LocalBroadcastManager.getInstance(it)
                .registerReceiver(loadResultsReceiver,
                    IntentFilter(DETAILS_INTENT_FILTER)
                )
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.liveDataToObserve.observe(viewLifecycleOwner, Observer {
            renderData(it)
        })
        arguments?.getParcelable<Weather>(BUNDLE_EXTRA)?.let {
            viewModel.loadWeather(it)
            //viewModel.getWeatherFromRemote()
            getWeather(it)
        }
    }

    private fun getWeather(weather: Weather) {
        viewModel.setLoading()
        context?.let {
            it.startService(Intent(it, DetailsService::class.java).apply {
                putExtra(
                    LATITUDE_EXTRA,
                    weather.city.lat
                )
                putExtra(
                    LONGITUDE_EXTRA,
                    weather.city.lon
                )
            })
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
                    mainView.visibility = View.VISIBLE
                }
            }
            is AppState.Loading -> {
                binding.mainView.visibility = View.GONE
                binding.detailFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.detailFragmentLoadingLayout.visibility = View.GONE
                Snackbar
                    .make(binding.root, "Error", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload") {
                        getWeather(viewModel.getWeatherFromModel())
                    }
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

    override fun onDestroy() {
        context?.let {
            LocalBroadcastManager.getInstance(it).unregisterReceiver(loadResultsReceiver)
        }
        super.onDestroy()
    }
}