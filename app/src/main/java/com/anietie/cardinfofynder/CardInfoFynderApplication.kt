package com.anietie.cardinfofynder

import android.app.Application
import com.anietie.cardinfofynder.core.di.appComponent
import com.microblink.blinkcard.MicroblinkSDK
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CardInfoFynderApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        configureDI()
        MicroblinkSDK.setLicenseKey("sRwAAAAaY29tLmFuaWV0aWUuY2FyZGluZm9meW5kZXKarrdeSCR58R41+XYUWdov7vWiRIL1YoypziNWwNzs6IFngJo3773JN94ay9qpH5OotkStLNcNcQj1zlaRsHFpIQs2wE5iQyy2pvgrQJZYV4jHgzV59g4xZ0oOgIfSkRi/y+/j9PduUlVr+e+1+uv4vTkuQu9pkgyl1GS2XngelfQs32SqBG7QTAERdldB2XnKfMlcPmF9CuLUM9/15oVCs6bAM3h1iLFpHlrdxKVA", this)
    }

    private fun configureDI() = startKoin {
        androidContext(this@CardInfoFynderApplication)
        modules(appComponent)
    }
}
