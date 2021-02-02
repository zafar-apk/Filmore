package com.kangaroo.filmore.Views.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kangaroo.filmore.Models.ApiFactory
import com.kangaroo.filmore.Models.MovieRepository
import com.kangaroo.filmore.Models.OneMovie
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SearchViewModel : ViewModel() {

    val liveQuery: MutableLiveData<String> = MutableLiveData()

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    private val repository : MovieRepository = MovieRepository(ApiFactory.tmdbApi)


    val foundMovieData = MutableLiveData<MutableList<OneMovie>>()
    val foundTvShowData = MutableLiveData<MutableList<OneMovie>>()

    fun findMovies(query: String){
        scope.launch {
            val foundMovieDataTemp = repository.searchMoviesInApi(query)
            foundMovieData.postValue(foundMovieDataTemp)
        }
    }

    fun findTvShows(query: String){
        scope.launch {
            val foundTvShowDataTemp = repository.searchTvShowsInApi(query)
            foundTvShowData.postValue(foundTvShowDataTemp)

        }
    }


    fun cancelAllRequests() = coroutineContext.cancel()

}



