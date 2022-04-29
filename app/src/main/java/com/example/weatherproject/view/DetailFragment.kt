package com.example.weatherproject.view

import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.weatherproject.R
import com.example.weatherproject.databinding.DetailFragmentBinding
import com.example.weatherproject.model.Weather
import com.example.weatherproject.utils.CITY_IMG_URL
import com.example.weatherproject.utils.getCondition
import com.example.weatherproject.utils.getConditionIconUrl
import com.example.weatherproject.utils.getGradus
import com.example.weatherproject.viewmodel.AppState
import com.example.weatherproject.viewmodel.DetailViewModel
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso


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
                    Picasso
                        .get()
                        .load(CITY_IMG_URL)
                        .into(headerIcon)
                    GlideToVectorYou.justLoadImage(
                        activity,
                        Uri.parse(getConditionIconUrl(appState.weather.icon)),
                        weatherImg
                    )

                    weatherCondition.text = getCondition(appState.weather.condition.toString())
                    temperatureValue.text = getGradus(appState.weather.temperature)
                    feelsLikeValue.text = getGradus(appState.weather.feelsLike)
                    cityName.text = appState.weather.city.city.toString()
                    cityCoordinates.text = String.format(
                        getString(R.string.city_coordinates),
                        appState.weather.city.lat.toString(),
                        appState.weather.city.lon.toString()
                    )
                    detailFragmentLoadingLayout.loadingLayout.visibility = View.GONE
                }
            }
            is AppState.Loading -> {
                binding.detailFragmentLoadingLayout.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.detailFragmentLoadingLayout.loadingLayout.visibility = View.GONE
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