package com.anietie.cardinfofynder.feature.data

import com.anietie.cardinfofynder.core.base.ResponseState
import com.anietie.cardinfofynder.feature.data.network.CardInfoService
import com.anietie.cardinfofynder.feature.data.network.model.CardInfo
import com.anietie.cardinfofynder.feature.domain.CardInfoFynderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CardInfoFynderRepositoryImpl(
    private val cardInfoService: CardInfoService
) : CardInfoFynderRepository {

    override fun getCardInfo(bin: String): Flow<ResponseState<CardInfo>> = flow {
        emit(ResponseState.loading())
        try {
            val cardDetails = cardInfoService.fetchCardInfo(bin).cardInfo
            emit(ResponseState.success(cardDetails))
        }
        catch (e: Exception) {
            emit(ResponseState.error<Nothing>("Encountered an error", e))
        }
    }
}
