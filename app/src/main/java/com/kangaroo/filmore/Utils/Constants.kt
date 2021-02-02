package com.kangaroo.filmore.Utils

import com.kangaroo.filmore.BuildConfig


object Constants {
    const val LOG_TAG = "moviesAppLog"
    const val PREF = "myPref"

    const val DATA_RESULT_FULLSCREEN = "Res_ok"
    const val MOVIE_ID = "id_of_movie"
    const val MOVIE_STARTS_TIME = "time_of_movie"
    const val DATA_POSITION = "item position$"
    var tmdbApiKey = BuildConfig.TMDB_API_KEY
    var apiLanguage = "ru"
}