package com.dc.pokapp

import android.app.Application
import com.dc.pokapp.di.Di
import org.koin.core.context.startKoin

class PokappApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(Di.networkModule)
        }
    }


}