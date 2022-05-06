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
            //historyLiveData.value = AppState.Loading
            historyLiveData.postValue(AppState.Loading)
            try {
                Thread {
                    historyLiveData.postValue(AppState.Success(historyRepository.getAllHistory()))
                    //historyLiveData.value =

                }.start()
            }
            catch (e:Exception){
                historyLiveData.postValue(AppState.Error(e))
            }
        }

    fun getSerchHistory(city : String) {
        historyLiveData.postValue(AppState.Loading)
        try {
             Thread {
                historyLiveData.postValue(AppState.Success(historyRepository.getSerchHistory(city)))
              }.start()
        }
        catch (e:Exception){
            historyLiveData.postValue(AppState.Error(e))
        }
    }

    }
