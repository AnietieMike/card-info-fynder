package com.anietie.cardinfofynder.feature.data.network

import com.anietie.cardinfofynder.feature.data.network.model.CardInfo
import retrofit2.http.GET
import retrofit2.http.Path

interface CardInfoService {

    @GET("/{bin}")
    suspend fun fetchCardInfo(@Path("bin") bin: String): CardInfo
}
