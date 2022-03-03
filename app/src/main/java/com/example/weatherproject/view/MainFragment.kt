package com.example.weatherproject.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherproject.R
import com.example.weatherproject.databinding.MainFragmentBinding
import com.example.weatherproject.model.Weather
import com.example.weatherproject.viewmodel.AppState
import com.example.weatherproject.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val adapter = MainFragmentAdapter(object : OnItemViewClickListener{
        override fun onItemViewClick(weather: Weather) {
            activity?.supportFragmentManager?.apply {
                this.beginTransaction()
                    .add(R.id.container, DetailFragment.newInstance(Bundle().apply {
                        putParcelable(DetailFragment.BUNDLE_EXTRA, weather)
                    }))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
    })
    private var isRus = true
    private lateinit var viewModel: MainViewModel

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            _binding = MainFragmentBinding.inflate(inflater, container, false)
            return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainFragmentRecyclerView.adapter = adapter
        binding.mainFragmentFAB.setOnClickListener{
            changeWeatherDataSet()
        }
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
//        val observer = Observer<AppState> {
//            renderData(it)
//        }
//        //viewModel.getLiveData().observe(viewLifecycleOwner, observer)
        viewModel.liveDataToObserve.observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getWeatherFromLocal(isRus)
    }

    private fun changeWeatherDataSet() =
        if (isRus) {
            binding.mainFragmentFAB.setImageResource(R.drawable.ic_earth)
        } else {
            binding.mainFragmentFAB.setImageResource(R.drawable.ic_russia)
        }.let {
            isRus = !isRus
            viewModel.getWeatherFromLocal(isRus)
        }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                //при успехе обновляем отображение информации
               adapter.setWeather(appState.weatherList)
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                //Snackbar.make(binding.root, "Success", Snackbar.LENGTH_LONG).show()
            }
            is AppState.Loading -> {
                binding.mainFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                Snackbar
                    .make(binding.root, "Error", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload") { viewModel.getWeatherFromLocal(isRus) }
                    .show()
            }
        }
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(weather: Weather)
    }

    override fun onDestroy() {
        _binding = null
        adapter.removeListener()
        super.onDestroy()
    }


}