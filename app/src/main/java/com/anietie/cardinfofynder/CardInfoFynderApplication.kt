package com.anietie.cardinfofynder

import android.app.Application
import com.anietie.cardinfofynder.core.di.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CardInfoFynderApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        configureDI()
    }

    private fun configureDI() = startKoin {
        androidContext(this@CardInfoFynderApplication)
        modules(appComponent)
    }
}
