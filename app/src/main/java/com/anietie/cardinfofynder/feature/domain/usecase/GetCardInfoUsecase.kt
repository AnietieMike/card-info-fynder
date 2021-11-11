package com.anietie.cardinfofynder.feature.domain.usecase

import com.anietie.cardinfofynder.core.base.ResponseState
import com.anietie.cardinfofynder.feature.data.network.model.CardInfo
import com.anietie.cardinfofynder.feature.domain.CardInfoFynderRepository
import com.chamsmobile.android.core.functional.base.FlowUseCase
import kotlinx.coroutines.flow.Flow

class GetCardInfoUsecase(
    private val cardInfoFynderRepository: CardInfoFynderRepository
) : FlowUseCase<String, CardInfo> {
    override fun invoke(params: String?): Flow<ResponseState<CardInfo>> {
        return cardInfoFynderRepository.getCardInfo(params!!)
    }
}
