package com.example.weatherproject.view

import android.content.Context.MODE_PRIVATE
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
import com.example.weatherproject.utils.IS_WORLD_KEY
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
        viewModel.liveDataToObserve.observe(viewLifecycleOwner, Observer { renderData(it) })
        ShowListOfTowns()
    }

    private fun ShowListOfTowns(){
        if (activity?.getPreferences(MODE_PRIVATE)?.getBoolean(IS_WORLD_KEY,false)?:true){
            viewModel.getWeatherFromLocal(isRus)
        }
        else{
           changeWeatherDataSet()
        }
    }



    private fun changeWeatherDataSet() =
        if (isRus) {
            binding.mainFragmentFAB.setImageResource(R.drawable.ic_earth)
        } else {
            binding.mainFragmentFAB.setImageResource(R.drawable.ic_russia)
        }.let {
            isRus = !isRus
            viewModel.getWeatherFromLocal(isRus)
            saveListOfTowns(isRus)
        }


    private fun saveListOfTowns(isRus: Boolean) {
        val editor = activity?.getPreferences(MODE_PRIVATE)?.edit()
        editor?.putBoolean(IS_WORLD_KEY,isRus)
        editor?.apply()
    }


    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                adapter.setWeather(appState.weatherList)
                binding.mainFragmentLoadingLayout.loadingLayout.visibility = View.GONE
            }
            is AppState.Loading -> {
                binding.mainFragmentLoadingLayout.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.mainFragmentLoadingLayout.loadingLayout.visibility = View.GONE
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