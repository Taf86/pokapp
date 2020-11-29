package com.dc.pokapp.source.database

import androidx.paging.LoadType
import androidx.paging.PagingSource
import androidx.room.*
import androidx.room.Dao
import com.dc.pokapp.model.Pokemon
import com.dc.pokapp.model.PokemonDetail
import com.dc.pokapp.model.RemoteKeys

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPokemons(pokemons: List<Pokemon>)

    @Query("SELECT * FROM pokemon")
    fun pokemons(): PagingSource<Int, Pokemon>

    @Query("DELETE FROM pokemon")
    suspend fun clearPokemons()

    @Query("SELECT * FROM pokemon_detail WHERE name = :name")
    suspend fun getPokemon(name: String): PokemonDetail?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(detail: PokemonDetail)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRemoteKeys(remoteKey: List<RemoteKeys>)

    @Query("SELECT * FROM remote_keys WHERE pokemonName = :name")
    suspend fun remoteKeysPokemonName(name: String): RemoteKeys?

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()


    @Transaction
    suspend fun updateDatabaseFromApi(
        loadType: LoadType,
        page: Int,
        endOfPaginationReached: Boolean,
        pokemons: List<Pokemon>
    ) {
        if (loadType == LoadType.REFRESH) {
            clearRemoteKeys()
            clearPokemons()
        }
        val prevKey = if (page == 0) null else page - 1
        val nextKey = if (endOfPaginationReached) null else page + 1
        val keys = pokemons.map {
            RemoteKeys(pokemonName = it.name, prevKey = prevKey, nextKey = nextKey)
        }
        insertAllRemoteKeys(keys)
        insertAllPokemons(pokemons)
    }

}