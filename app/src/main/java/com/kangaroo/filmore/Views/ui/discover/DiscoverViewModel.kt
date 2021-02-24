package com.kangaroo.filmore.Views.ui.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.kangaroo.filmore.Models.ApiFactory
import com.kangaroo.filmore.Models.MovieDataSource
import com.kangaroo.filmore.Models.MovieRepository
import com.kangaroo.filmore.Utils.Constants

class DiscoverViewModel : ViewModel() {

    private val repository : MovieRepository = MovieRepository(ApiFactory.tmdbApi)
    private val methodType = arrayOf(Constants.METHOD_DISCOVERY)
    val listData = Pager(PagingConfig(pageSize = 20)) {
        MovieDataSource(repository, methodType)
    }.flow.cachedIn(viewModelScope)


}

