package com.example.weatherproject.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherproject.R
import com.example.weatherproject.databinding.HistoryFragmentBinding
import com.example.weatherproject.viewmodel.AppState
import com.example.weatherproject.viewmodel.HistoryViewModel
import com.google.android.material.snackbar.Snackbar

class HistoryFragment  : Fragment() {
        private var _binding: HistoryFragmentBinding? = null
        private val binding get() = _binding!!
        private val viewModel: HistoryViewModel by lazy {
            ViewModelProvider(this).get(HistoryViewModel::class.java) }

        private val adapter: HistoryFragmentAdapter by lazy { HistoryFragmentAdapter() }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            _binding = HistoryFragmentBinding.inflate(inflater, container, false)
            return binding.root
        }
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            binding.historyFragmentRecyclerview.adapter = adapter
            viewModel.historyLiveData.observe(viewLifecycleOwner, Observer {
                renderData(it) })
            viewModel.getAllHistory()
            binding.buttonSerch.setOnClickListener {
                if(binding.serchText.text.toString()!=""){
                    viewModel.getSerchHistory(binding.serchText.text.toString())
                }
            }
        }
        private fun renderData(appState: AppState) {
            when (appState) {
                is AppState.Success -> {
                    binding.historyFragmentRecyclerview.visibility = View.VISIBLE
                    binding.includedLoadingLayout.loadingLayout.visibility =
                        View.GONE
                    adapter.setData(appState.weatherList)
                }
                is AppState.Loading -> {
                    binding.historyFragmentRecyclerview.visibility = View.GONE
                    binding.includedLoadingLayout.loadingLayout.visibility =
                        View.VISIBLE
                }
                is AppState.Error -> {
                    binding.historyFragmentRecyclerview.visibility = View.VISIBLE
                    binding.includedLoadingLayout.loadingLayout.visibility =
                        View.GONE
                    Snackbar
                        .make(binding.root, appState.error.toString(), Snackbar.LENGTH_INDEFINITE)
                        .setAction("Reload") {  viewModel.getAllHistory() }
                        .show()
                }
            }
        }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        @JvmStatic
        fun newInstance() =
            HistoryFragment()
    }
}