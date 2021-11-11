package com.anietie.cardinfofynder.core.di

import com.anietie.cardinfofynder.feature.data.CardInfoFynderRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    single {
        CardInfoFynderRepositoryImpl(
            cardInfoService = get(),
        )
    }
}
