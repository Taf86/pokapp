package com.dc.pokapp.paging

import androidx.paging.PagingSource
import com.dc.pokapp.model.Pokemon
import com.dc.pokapp.repository.Repository

class ListPagedSource(
    private val repo: Repository
) : PagingSource<Int, Pokemon>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        return try {
            val nextPageNumber = params.key ?: 0
            val response = repo.getList(nextPageNumber * 10, 10)
            LoadResult.Page(
                data = response.results,
                prevKey = response.previous?.let{nextPageNumber - 1},
                nextKey = response.next?.let{nextPageNumber + 1}
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}