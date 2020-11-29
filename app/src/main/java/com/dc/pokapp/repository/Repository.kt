package com.dc.pokapp.repository

import androidx.paging.LoadType
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dc.pokapp.model.*
import com.dc.pokapp.paging.PokemonRemoteMediator
import com.dc.pokapp.source.database.Dao
import com.dc.pokapp.source.network.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class Repository(
    private val api: Api,
    private val dao: Dao
) {

    private fun prefetchDetails(names: List<String>) {
        GlobalScope.launch {
            combine(names.map { name -> prefetchDetail(name) }) {}.collect()
        }
    }

    private fun prefetchDetail(name: String): Flow<Unit> = flow {
        try {
            dao.insert(api.getPokemonDetail(name))
            emit(Unit)
        } catch (t: Throwable) {
            emit(Unit)
        }
    }.flowOn(Dispatchers.IO)

    suspend fun searchPokemons(page: Int, pageSize: Int): LocalPage {
        val serverPage = api.getPokemonPage(page * pageSize, pageSize)
        prefetchDetails(serverPage.results.map { p -> p.name })
        return LocalPage(
            total = serverPage.count,
            items = serverPage.results,
            nextPage = serverPage.next?.let { page + 1 }
        )
    }

    suspend fun getPokemonDetail(name: String): PokemonDetail {
        return try {
            val pokemon = api.getPokemonDetail(name)
            dao.insert(pokemon)
            pokemon
        } catch (t: Throwable) {
            dao.getPokemon(name) ?: throw t
        }
    }

    suspend fun updateDatabaseFromApi(
        loadType: LoadType,
        page: Int,
        endOfPaginationReached: Boolean,
        pokemons: List<Pokemon>
    ) = dao.updateDatabaseFromApi(loadType, page, endOfPaginationReached, pokemons)

    suspend fun remoteKeysPokemonName(name: String) = dao.remoteKeysPokemonName(name)


    fun getPokemonsStream(): Flow<PagingData<Pokemon>> {
        val pagingSourceFactory = { dao.pokemons() }

        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            remoteMediator = PokemonRemoteMediator(this),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }


}