package com.dc.pokapp.repository

import com.dc.pokapp.model.*
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

    suspend fun getList(offset: Int, limit: Int): LocalPage<Pokemon> {
        return try {
            val serverPage = api.getPokemonPage(offset, limit)
            dao.insert(ServerTotalCount(totalCount = serverPage.count))
            dao.insertAll(serverPage.results)
            prefetchDetails(serverPage.results.map { p -> p.name })
            LocalPage(
                next = serverPage.next?.let { (offset / limit) + 1 },
                previous = ((offset / limit) - 1).let { if (it >= 0) it else null },
                results = serverPage.results
            )
        } catch (t: Throwable) {
            val serverTotalCount = dao.getServerTotalCount()?.totalCount
            val records = dao.getPokemonPage(offset, limit)
            LocalPage(
                next = when {
                    records.size >= limit -> (offset / limit) + 1
                    serverTotalCount == null || offset + records.size < serverTotalCount -> (offset / limit)
                    else -> null
                },
                previous = ((offset / limit) - 1).let { if (it >= 0) it else null },
                results = records
            )
        }
    }

    suspend fun getDetail(name: String): PokemonDetail {
        return try {
            val pokemon = api.getPokemonDetail(name)
            dao.insert(pokemon)
            pokemon
        } catch (t: Throwable) {
            dao.getPokemon(name) ?: throw t
        }
    }


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
}