package com.anietie.cardinfofynder.feature.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anietie.cardinfofynder.core.state.ResponseState
import com.anietie.cardinfofynder.feature.data.CardInfoFynderRepositoryImpl
import com.anietie.cardinfofynder.feature.data.network.model.CardInfo
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CardInfoViewModel constructor(
    private val cardInfoFynderRepositoryImpl: CardInfoFynderRepositoryImpl
) : ViewModel() {

    private val _result: MutableLiveData<ResponseState<CardInfo>> = MutableLiveData()
    val result: LiveData<ResponseState<CardInfo>>
        get() = _result

    fun getCardInfo(bin: String) {
        _result.value = ResponseState.loading()
        viewModelScope.launch {
            try {
                cardInfoFynderRepositoryImpl.getCardInfo(bin).collect {
                    _result.value = it
                    Log.d("Viewmodel result", "getCardInfo: ${_result.value}")
                }
            } catch (e: Exception) {
                _result.value = null
                println("An error occurred!")
            }
        }
    }
}
