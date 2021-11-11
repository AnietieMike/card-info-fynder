package com.anietie.cardinfofynder.core.di

import com.anietie.cardinfofynder.core.base.ApiServiceFactory
import org.koin.dsl.module

val networkModule = module {

    factory { ApiServiceFactory }
    factory { get<ApiServiceFactory>().createApiService(isDebug) }
}
const val isDebug = true
