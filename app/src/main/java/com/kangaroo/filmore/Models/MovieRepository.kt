package com.kangaroo.filmore.Models

class MovieRepository(private val api : ApiTMDB) : BaseRepository() {

    // Получение списка популярных фильмов. Суспенд !!
    suspend fun getPopularMovies() : MutableList<OneMovie>? {

        val movieResponse = safeApiCall(
            call = {api.getMovieList().await()},
            errorMessage = "Error Fetching Popular Movies"
        )
        return movieResponse?.results?.toMutableList()

    }

    suspend fun getMovieVideo(movieId: String): MutableList<VideoOneMovie>? {

        val movieResponse = safeApiCall(
            call = {api.getMovieVideo(movieId).await()},
            errorMessage = "Error Fetching Popular Movies"
        )
        return movieResponse?.results?.toMutableList()

    }

    suspend fun searchMoviesInApi(query: String) : MutableList<OneMovie>? {

        val movieResponse = safeApiCall(
            call = {api.getSearchedData(query).await()},
            errorMessage = "Error Fetching Popular Movies"
        )
        return movieResponse?.results?.toMutableList()

    }



}