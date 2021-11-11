package com.anietie.cardinfofynder.feature.domain

import com.anietie.cardinfofynder.core.base.ResponseState
import com.anietie.cardinfofynder.feature.data.network.model.CardInfo
import kotlinx.coroutines.flow.Flow

interface CardInfoFynderRepository {
    fun getCardInfo(bin: String): Flow<ResponseState<CardInfo>>
}