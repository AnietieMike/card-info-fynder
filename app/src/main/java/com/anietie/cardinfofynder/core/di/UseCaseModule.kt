package com.anietie.cardinfofynder.core.di

import com.anietie.cardinfofynder.feature.domain.usecase.GetCardInfoUsecase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetCardInfoUsecase(get()) }
}
