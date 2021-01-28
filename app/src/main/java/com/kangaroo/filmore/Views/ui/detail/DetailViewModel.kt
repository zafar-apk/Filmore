package com.kangaroo.filmore.Views.ui.detail

import androidx.lifecycle.ViewModel
import com.kangaroo.filmore.Models.Repository

class DetailViewModel : ViewModel() {
    private val repository = Repository()

    fun getMovieVideo(movieId: String) = repository.getMovieVideo(movieId)

}