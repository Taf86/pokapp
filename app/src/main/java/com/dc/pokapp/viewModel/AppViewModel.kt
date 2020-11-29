package com.dc.pokapp.viewModel

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dc.pokapp.event.AppEvent
import com.dc.pokapp.model.Pokemon
import com.dc.pokapp.repository.Repository
import com.dc.pokapp.state.AppState
import io.uniflow.androidx.flow.AndroidDataFlow
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AppViewModel(
    private val repo: Repository
) : AndroidDataFlow() {

    private var searchJob: Job? = null
    fun search() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            repo.getPokemonsStream()
                .cachedIn(viewModelScope).collectLatest {
                sendPagingData(it)
            }
        }
    }

    init {
        search()
    }

    private fun sendPagingData(pagingData: PagingData<Pokemon>) = action {
        sendEvent(AppEvent.SendPagingData(pagingData))
    }

    fun openPokemonDetail(name: String) = action {
        try {
            val detail = repo.getPokemonDetail(name)
            setState { AppState.Pokemon(detail) }
            sendEvent(AppEvent.DetailReady)
        } catch (t: Throwable) {
            sendEvent(AppEvent.DetailError(t))
        }
    }


}

