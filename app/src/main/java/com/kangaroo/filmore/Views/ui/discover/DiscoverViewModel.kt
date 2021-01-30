package com.kangaroo.filmore.Views.ui.discover

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kangaroo.filmore.Models.ApiFactory
import com.kangaroo.filmore.Models.MovieRepository
import com.kangaroo.filmore.Models.OneMovie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DiscoverViewModel : ViewModel() {

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    private val repository : MovieRepository = MovieRepository(ApiFactory.tmdbApi)


    val popularMoviesLiveData = MutableLiveData<MutableList<OneMovie>>()

    fun fetchMovies(){
        scope.launch {
            val popularMovies = repository.getPopularMovies()
            popularMoviesLiveData.postValue(popularMovies)
        }
    }
}