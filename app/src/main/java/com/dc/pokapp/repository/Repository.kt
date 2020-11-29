package com.dc.pokapp.repository

import com.dc.pokapp.model.Page
import com.dc.pokapp.model.Pokemon
import com.dc.pokapp.model.PokemonDetail
import com.dc.pokapp.source.database.Dao
import com.dc.pokapp.source.network.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class Repository(
    private val api: Api,
    private val dao: Dao
) {

    suspend fun getList(offset: Int, limit: Int): Page<Pokemon> {
        return try {
            val page = api.getPokemonPage(offset, limit)
            dao.insertAll(page.results)
            prefetchDetails(page.results.map { p -> p.name })
            page
        } catch (t: Throwable) {
            val records = dao.getPokemonPage(offset, limit)
            Page(
                next = if (records.size < limit) null else "",
                previous = if (offset > 0) "" else null,
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