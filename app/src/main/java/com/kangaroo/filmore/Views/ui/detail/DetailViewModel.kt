package com.kangaroo.filmore.Views.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kangaroo.filmore.Models.ApiFactory
import com.kangaroo.filmore.Models.MovieRepository
import com.kangaroo.filmore.Models.VideoOneMovie
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class DetailViewModel : ViewModel() {

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
    get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    private val repository = MovieRepository(ApiFactory.tmdbApi)

    val movieVideo = MutableLiveData<MutableList<VideoOneMovie>>()

    fun getVideoOfVideo (videoId: String){

        scope.launch {
            val videoOfMovie = repository.getMovieVideo(videoId)
            movieVideo.postValue(videoOfMovie)
        }

    }

    fun cancelRequest() = coroutineContext.cancel()



}