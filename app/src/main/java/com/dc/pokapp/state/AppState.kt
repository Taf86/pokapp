package com.dc.pokapp.state

import com.dc.pokapp.model.PokemonDetail
import io.uniflow.core.flow.data.UIState

sealed class AppState : UIState() {
    class Pokemon(val detail: PokemonDetail) : AppState()
}

