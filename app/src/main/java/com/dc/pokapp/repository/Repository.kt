package com.dc.pokapp.repository

import com.dc.pokapp.source.network.Api


class Repository(
    private val api: Api
) {


    suspend fun getList(offset: Int, limit: Int) = api.getPokemonPage(offset, limit)

}