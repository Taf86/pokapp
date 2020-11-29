package com.dc.pokapp.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.dc.pokapp.model.Pokemon
import com.dc.pokapp.model.RemoteKeys
import com.dc.pokapp.repository.Repository
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

@OptIn(ExperimentalPagingApi::class)
class PokemonRemoteMediator(
    private val repository: Repository
) : RemoteMediator<Int, Pokemon>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Pokemon>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 0
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                    ?: throw InvalidObjectException("Remote key and the prevKey should not be null")
                remoteKeys.prevKey ?: return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                remoteKeys?.nextKey
                    ?: throw InvalidObjectException("Remote key should not be null for $loadType")
            }

        }

        return try {
            val apiResponse = repository.searchPokemons(page, state.config.pageSize)
            val pokemons = apiResponse.items
            val endOfPaginationReached = pokemons.isEmpty()
            repository.updateDatabaseFromApi(loadType, page, endOfPaginationReached, pokemons)
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Pokemon>): RemoteKeys? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { pokemon ->
                repository.remoteKeysPokemonName(pokemon.name)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Pokemon>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { pokemon ->
                repository.remoteKeysPokemonName(pokemon.name)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Pokemon>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.name?.let { pokemonName ->
                repository.remoteKeysPokemonName(pokemonName)
            }
        }
    }

}