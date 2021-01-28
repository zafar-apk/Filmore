package com.kangaroo.filmore.Views.ui.discover

import androidx.lifecycle.ViewModel
import com.kangaroo.filmore.Models.Repository

class DiscoverViewModel : ViewModel() {

    private val repository = Repository()
    private val _listData = repository.getDiscoverListMovie()

    val listData = _listData
}