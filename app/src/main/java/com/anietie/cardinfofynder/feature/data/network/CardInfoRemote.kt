package com.anietie.cardinfofynder.feature.data.network

interface CardInfoRemote {
    suspend fun fetchCardInfo(bin: String): Result<Any>
}