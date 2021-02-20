package com.kangaroo.filmore.Models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/** Data classes objects for getting Response and Json Converter*/

// Object of Video details and name e. t. c
@Parcelize
data class OneMovie(var id: Int? = null,
                    var title: String? = null,
                    var poster_path: String? = null,
                    var overview: String? = null,
                    var original_title: String? = null,
                    var release_date: String? = null,
                    var first_air_date: String? = null,
                    var name: String? = null,
                    var backdrop_path: String? = null,
                    var original_name: String? = null): Parcelable

// Response from api -> Movie
data class Movie(var results: List<OneMovie>? = null)

// Object of Video from array of Movie objects
data class VideoOneMovie(var key: String? = null)

//Object with array of VideoOneMovie objects
data class VideoMovie(var results: List<VideoOneMovie>? = null)


