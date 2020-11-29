package com.dc.pokapp.view

import com.dc.pokapp.di.Di
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : android.app.Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(Di.modules)
            androidContext(this@Application)
        }

    }


}