package com.kangaroo.filmore.Views.ui.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.kangaroo.filmore.Models.ApiFactory
import com.kangaroo.filmore.Models.MovieDataSource
import com.kangaroo.filmore.Models.MovieRepository

class DiscoverViewModel : ViewModel() {

    private val repository : MovieRepository = MovieRepository(ApiFactory.tmdbApi)

    val listData = Pager(PagingConfig(pageSize = 20)) {
        MovieDataSource(repository)
    }.flow.cachedIn(viewModelScope)


}

