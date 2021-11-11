package com.anietie.cardinfofynder.feature.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anietie.cardinfofynder.core.base.ResponseState
import com.anietie.cardinfofynder.feature.data.network.model.CardInfo
import com.anietie.cardinfofynder.feature.domain.usecase.GetCardInfoUsecase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CardInfoViewModel constructor(
    private val getCardInfoUsecase: GetCardInfoUsecase
) : ViewModel() {

    private val _result: MutableLiveData<ResponseState<CardInfo>> = MutableLiveData()
    val result: LiveData<ResponseState<CardInfo>>
        get() = _result

    fun getCardInfo(bin: String) {
        viewModelScope.launch {
            try {
                getCardInfoUsecase(bin).collect {
                    _result.value = it
                }
            } catch (e: Exception) {
                println("Something went wrong!")
            }
        }
    }
}
