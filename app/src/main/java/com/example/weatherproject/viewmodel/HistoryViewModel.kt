package com.example.weatherproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherproject.app.App.Companion.getHistoryDao
import com.example.weatherproject.room.LocalRepository
import com.example.weatherproject.room.LocalRepositoryImpl

class HistoryViewModel(
    val historyLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val historyRepository: LocalRepository =
            LocalRepositoryImpl(getHistoryDao())
    ) : ViewModel() {
        fun getAllHistory() {
            historyLiveData.value = AppState.Loading
            try {
               // Thread {
                    historyLiveData.value =
                        AppState.Success(historyRepository.getAllHistory())
              //  }.start()
            }
            catch (e:Exception){
                historyLiveData.postValue(AppState.Error(e))
            }

        }
    }
