package com.anietie.cardinfofynder.feature.data.network.model

data class CardInfo(
    val bank: Bank,
    val brand: String,
    val country: Country,
    val number: Number,
    val prepaid: Boolean,
    val scheme: String,
    val type: String
)