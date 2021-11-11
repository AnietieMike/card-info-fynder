package com.anietie.cardinfofynder.core.di

import com.anietie.cardinfofynder.feature.CardInfoFynderRepository
import com.decagon.anietie.simplecrypto.model.SimpleCryptoRepository
import com.decagon.anietie.simplecrypto.model.remote.model.BitcoinModelMapper
import com.decagon.anietie.simplecrypto.model.remote.model.DashModelMapper
import com.decagon.anietie.simplecrypto.model.remote.model.DogecoinModelMapper
import com.decagon.anietie.simplecrypto.model.remote.model.EthereumModelMapper
import com.decagon.anietie.simplecrypto.model.remote.model.LitecoinModelMapper
import com.decagon.anietie.simplecrypto.model.remote.model.RippleModelMapper
import org.koin.dsl.module

val repositoryModule = module {

    factory { BitcoinModelMapper() }

    single {
        CardInfoFynderRepository(
            apiService = get(),
        )
    }
}
