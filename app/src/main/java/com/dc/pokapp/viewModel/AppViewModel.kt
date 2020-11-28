package com.dc.pokapp.viewModel

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dc.pokapp.event.AppEvent
import com.dc.pokapp.model.Pokemon
import com.dc.pokapp.paging.ListPagedSource
import com.dc.pokapp.repository.Repository
import com.dc.pokapp.state.AppState
import io.uniflow.androidx.flow.AndroidDataFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AppViewModel(
    private val listPagedSource: ListPagedSource,
    private val repo: Repository
) : AndroidDataFlow() {

    init {
        viewModelScope.launch {
            Pager(PagingConfig(pageSize = 10)) {
                listPagedSource
            }
                .flow
                .cachedIn(viewModelScope)
                .collectLatest {
                    sendPagingData(it)
                }
        }
    }

    private fun sendPagingData(pagingData: PagingData<Pokemon>) = action {
        sendEvent(AppEvent.SendPagingData(pagingData))
    }

    fun openPokemonDetail(name: String) = action {
        sendEvent(AppEvent.DetailLoading)
        try {
            val detail = repo.getDetail(name)
            setState { AppState.Pokemon(detail) }
            sendEvent(AppEvent.DetailReady)
        } catch (t: Throwable) {
            sendEvent(AppEvent.DetailError(t))
        }
    }


}

