package com.dc.pokapp.source.network

import com.dc.pokapp.model.Page
import com.dc.pokapp.model.Pokemon
import com.dc.pokapp.model.PokemonDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("pokemon/")
    suspend fun getPokemonPage(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Page<Pokemon>

    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(
        @Path("name") name: String
    ): PokemonDetail

}