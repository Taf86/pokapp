package com.dc.pokapp.event

import androidx.paging.PagingData
import com.dc.pokapp.model.Pokemon
import io.uniflow.core.flow.data.UIEvent

sealed class AppEvent : UIEvent() {
    data class SendPagingData(val pagingData: PagingData<Pokemon>) : AppEvent()
    data class DetailError(val error: Throwable) : AppEvent()
    object DetailReady : AppEvent()
}