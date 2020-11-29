package com.dc.pokapp.source.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dc.pokapp.model.Pokemon
import com.dc.pokapp.model.PokemonDetail

@Dao
interface Dao {

    @Query("SELECT * FROM pokemon LIMIT :limit OFFSET :offset")
    suspend fun getPokemonPage(offset: Int, limit: Int): List<Pokemon>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<Pokemon>)

    @Query("SELECT * FROM pokemon_detail WHERE name = :name")
    suspend fun getPokemon(name: String): PokemonDetail?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(detail: PokemonDetail)

}