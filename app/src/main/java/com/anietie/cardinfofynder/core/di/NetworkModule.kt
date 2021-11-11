package com.anietie.cardinfofynder.core.di

import com.decagon.anietie.simplecrypto.model.remote.ApiServiceFactory
import org.koin.dsl.module

val networkModule = module {

    factory { ApiServiceFactory }
    factory { get<ApiServiceFactory>().createApiService(isDebug) }
}
const val isDebug = true
