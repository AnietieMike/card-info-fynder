package com.anietie.cardinfofynder.feature.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anietie.cardinfofynder.core.navigation.NavManager
import com.anietie.cardinfofynder.core.state.ResponseState
import com.anietie.cardinfofynder.feature.data.CardInfoFynderRepositoryImpl
import com.anietie.cardinfofynder.feature.data.network.model.CardInfo
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CardInfoViewModel constructor(
    private val navManager: NavManager,
    private val cardInfoFynderRepositoryImpl: CardInfoFynderRepositoryImpl
) : ViewModel() {

    private val _result: MutableLiveData<ResponseState<CardInfo>> = MutableLiveData()
    val result: LiveData<ResponseState<CardInfo>>
        get() = _result

    fun getCardInfo(bin: String) {
        viewModelScope.launch {
            try {
                cardInfoFynderRepositoryImpl.getCardInfo(bin).collect {
                    _result.value = it
                    Log.d("Viewmodel try block", "$it")
                }
            } catch (e: Exception) {
                println("Something went wrong!")
            }
        }
    }

    fun navigateToCardDetails(cardBrand: String, cardType: String, bank: String, country: String) {
        val navDirections = HomeFragmentDirections.actionHomeFragmentToCardInfoFragment(cardBrand, cardType, bank, country)
        navManager.navigate(navDirections)
    }
}
