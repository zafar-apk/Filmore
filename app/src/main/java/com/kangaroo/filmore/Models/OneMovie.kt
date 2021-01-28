package com.kangaroo.filmore.Models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class OneMovie(
    var id: Int? = null,
    var title:String? = null,
    var poster_path:String? = null,
    var release_date:String? = null,
    var overview:String? = null,
    var original_title:String? = null) : Parcelable

