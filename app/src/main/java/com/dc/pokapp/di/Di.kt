package com.dc.pokapp.di

import com.dc.pokapp.source.network.Network
import org.koin.dsl.module

object Di {

    val networkModule = module {
        single { Network.buildRetrofit() }
        factory { Network.buildApi(get()) }
    }

}