package com.kangaroo.filmore.Models

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kangaroo.filmore.Utils.Constants
import com.kangaroo.filmore.Utils.Constants.LOG_TAG

class MovieDataSource(
    private val repository: MovieRepository,
    private val methodType: Array<String>
): PagingSource<Int, OneMovie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, OneMovie> {

        try {
            val currentLoadingPageKey = params.key ?: 1
            var response: MutableList<OneMovie>? = null
            methodType.forEach {
                Log.d(LOG_TAG, it)
            }
            when(methodType[0]){
                    Constants.METHOD_HOME_DETAILS -> {
                        response = repository.getTrendingListInApi(page = currentLoadingPageKey,
                            contentType = methodType[1],
                            timeWindow = methodType[2]
                        )
                    }
                    Constants.METHOD_DISCOVERY -> {
                        response = repository.getPopularMovies(currentLoadingPageKey)
                    }
                }

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
