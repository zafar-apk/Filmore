package com.kangaroo.filmore.Views.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kangaroo.filmore.Models.OneMovie
import com.kangaroo.filmore.Models.Repository

class SearchViewModel : ViewModel() {

    val query = MutableLiveData<String>()

    private val repository = Repository()

    var liveListOfMovies: MutableLiveData<List<OneMovie>>? = null

    fun searchMoviesAndTV(query: String){
        repository.getSearchedData(query)
        liveListOfMovies?.value = repository.movies?.value
    }

}



