package com.dc.pokapp.di

import com.dc.pokapp.paging.ListPagedSource
import com.dc.pokapp.repository.Repository
import com.dc.pokapp.source.network.Network
import com.dc.pokapp.viewModel.ListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object Di {

    private val sourcesModule = module {
        single { Network.buildRetrofit() }
        factory { Network.buildApi(get()) }
    }

    private val repositoryModule = module {
        factory { Repository(get()) }
    }


    private val pagedDataSourceModule = module {
        factory { ListPagedSource(get()) }
    }


    private val viewModelModule = module {
        viewModel { ListViewModel(get()) }
    }


    val modules
        get() = listOf(
            sourcesModule,
            repositoryModule,
            pagedDataSourceModule,
            viewModelModule
        )
}