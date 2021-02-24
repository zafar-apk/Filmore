package com.kangaroo.filmore.Views.ui.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kangaroo.filmore.Models.ApiFactory
import com.kangaroo.filmore.Models.MovieDataSource
import com.kangaroo.filmore.Models.MovieRepository
import com.kangaroo.filmore.Models.OneMovie
import com.kangaroo.filmore.Utils.Constants
import com.kangaroo.filmore.Utils.Constants.LOG_TAG
import kotlinx.coroutines.flow.Flow

class HomeDetailsViewModel: ViewModel() {
    fun setDataToViewModel(contentType: String, timeWindow: String){
        Log.d(LOG_TAG, "cp:$contentType,time:$timeWindow")
        val methodType = arrayOf(Constants.METHOD_HOME_DETAILS, contentType, timeWindow)
        listData = Pager(PagingConfig(pageSize = 20)) {
            MovieDataSource(repository, methodType)
        }.flow.cachedIn(viewModelScope)
    }

    private val repository : MovieRepository = MovieRepository(ApiFactory.tmdbApi)

    var listData: Flow<PagingData<OneMovie>>? = null
}