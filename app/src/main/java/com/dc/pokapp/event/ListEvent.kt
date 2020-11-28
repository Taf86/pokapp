package com.dc.pokapp.event

import androidx.paging.PagingData
import com.dc.pokapp.model.Pokemon
import io.uniflow.core.flow.data.UIEvent

sealed class ListEvent : UIEvent() {
    data class SendPagingData(val pagingData: PagingData<Pokemon>) : ListEvent()
}