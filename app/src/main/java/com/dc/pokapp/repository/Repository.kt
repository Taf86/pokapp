package com.dc.pokapp.repository

import com.dc.pokapp.model.Page
import com.dc.pokapp.model.Pokemon
import com.dc.pokapp.source.network.Api


class Repository(private val api: Api) {


    suspend fun getList(offset: Int, limit: Int): Page<Pokemon> {
        return api.getPokemonPage(offset, limit)
    }

}