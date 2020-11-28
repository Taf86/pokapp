package com.dc.pokapp.viewModel

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dc.pokapp.event.ListEvent
import com.dc.pokapp.model.Pokemon
import com.dc.pokapp.paging.ListPagedSource
import io.uniflow.androidx.flow.AndroidDataFlow
import io.uniflow.core.threading.launchOnIO
import kotlinx.coroutines.flow.collectLatest

class ListViewModel(
    private val listPagedSource: ListPagedSource
) : AndroidDataFlow() {

    init {
        viewModelScope.launchOnIO {
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
        sendEvent(ListEvent.SendPagingData(pagingData))
    }
}

