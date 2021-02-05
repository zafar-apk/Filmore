package com.kangaroo.filmore.Models

import androidx.paging.PagingSource
import androidx.paging.PagingState

class MovieDataSource(
    private val repository: MovieRepository
): PagingSource<Int, OneMovie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, OneMovie> {

        try {
            val currentLoadingPageKey = params.key ?: 1

                val response = repository.getPopularMovies(currentLoadingPageKey)
                val responseData = mutableListOf<OneMovie>()
                val data = response ?: emptyList()
                responseData.addAll(data)
                val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = currentLoadingPageKey.plus(1)
            )

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, OneMovie>): Int? {
        return state.anchorPosition
    }


}
