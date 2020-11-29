package com.dc.pokapp.di

import com.dc.pokapp.paging.ListPagedSource
import com.dc.pokapp.repository.Repository
import com.dc.pokapp.source.database.AppDatabase
import com.dc.pokapp.source.network.Network
import com.dc.pokapp.viewModel.AppViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object Di {

    private val sourcesModule = module {
        single { Network.buildRetrofit() }
        factory { Network.buildApi(get()) }
        single { AppDatabase.buildAppDatabase(androidContext()) }
        factory { AppDatabase.buildDao(get()) }
    }

    private val repositoryModule = module {
        factory { Repository(get(), get()) }
    }


    private val pagedDataSourceModule = module {
        factory { ListPagedSource(get()) }
    }


    private val viewModelModule = module {
        viewModel { AppViewModel(get(), get()) }
    }


    val modules
        get() = listOf(
            sourcesModule,
            repositoryModule,
            pagedDataSourceModule,
            viewModelModule
        )
}