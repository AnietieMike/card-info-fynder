package com.anietie.cardinfofynder.core.di

import com.anietie.cardinfofynder.feature.presentation.CardInfoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { CardInfoViewModel(get()) }
}