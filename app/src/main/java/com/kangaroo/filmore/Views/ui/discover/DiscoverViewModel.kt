package com.kangaroo.filmore.Views.ui.discover

import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.kangaroo.filmore.Models.ApiFactory
import com.kangaroo.filmore.Models.MovieDataSourceFactory
import com.kangaroo.filmore.Models.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class DiscoverViewModel : ViewModel() {

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    private val repository : MovieRepository = MovieRepository(ApiFactory.tmdbApi)

    val source = MovieDataSourceFactory(repository, scope)

    val livePagedList = LivePagedListBuilder(source, pagedListConfig()).build()

    private fun pagedListConfig() = PagedList.Config.Builder()
        .setPageSize(20)
        .setEnablePlaceholders(false)
        .build()

}

