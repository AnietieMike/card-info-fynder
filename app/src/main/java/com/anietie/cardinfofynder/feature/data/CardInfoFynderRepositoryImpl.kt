package com.anietie.cardinfofynder.feature.data

import android.util.Log
import com.anietie.cardinfofynder.core.state.ResponseState
import com.anietie.cardinfofynder.feature.data.network.CardInfoService
import com.anietie.cardinfofynder.feature.data.network.model.CardInfo
import com.anietie.cardinfofynder.feature.domain.CardInfoFynderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CardInfoFynderRepositoryImpl constructor(
    private val cardInfoService: CardInfoService
) : CardInfoFynderRepository {

    override fun getCardInfo(bin: String): Flow<ResponseState<CardInfo>> = flow {
        emit(ResponseState.loading())
        try {
            val cardDetails = cardInfoService.fetchCardInfo(bin)
            Log.d("RepoSuccessResponse", "getCardInfo: $cardDetails")
            emit(ResponseState.success(cardDetails))
        } catch (e: Exception) {
            emit(ResponseState.error<Nothing>("Encountered an error", e))
        }
    }
}
