package com.kangaroo.filmore.Utils

import com.kangaroo.filmore.Models.OneMovie

interface OnItemClickListener {
    fun onItemClick(id: String, oneMovie: OneMovie)
}