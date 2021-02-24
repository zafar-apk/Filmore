package com.kangaroo.filmore.Utils

import com.kangaroo.filmore.BuildConfig


object Constants {
    const val MEDIA_TYPE_TV = "data_tvs"
    const val MEDIA_TYPE_MOVIE = "data_movies"
    const val DATA_MEDIA_TYPE = "com.kangaroo.media_type"
    const val LOG_TAG = "moviesAppLog"
    const val PREF = "myPref"

    const val DATA_RESULT_FULLSCREEN = "Res_ok"
    const val MOVIE_ID = "id_of_movie"
    const val MOVIE_STARTS_TIME = "time_of_movie"
    const val DATA_POSITION = "item position$"

    const val METHOD_DISCOVERY = "DISCOVERY MODE"
    const val METHOD_HOME_DETAILS = "HOME DETAILS MODE"

    var tmdbApiKey = BuildConfig.TMDB_API_KEY
    var apiLanguage = "ru"
}