package com.kangaroo.filmore.Models

class MovieRepository(private val api : ApiTMDB) : BaseRepository() {

    // Получение списка популярных фильмов. Суспенд !!
    suspend fun getPopularMovies(page: Int) : MutableList<OneMovie>? {

        val movieResponse = safeApiCall(
            call = {api.getMovieListAsync(page).await()},
            errorMessage = "Error Fetching Popular Movies"
        )
        return movieResponse?.results?.toMutableList()

    }


    suspend fun getMovieVideo(movieId: String): MutableList<VideoOneMovie>? {

        val movieResponse = safeApiCall(
            call = {api.getMovieVideoAsync(movieId).await()},
            errorMessage = "Error getting movie video Popular Movies"
        )
        return movieResponse?.results?.toMutableList()

    }

    suspend fun searchMoviesInApi(query: String) : MutableList<OneMovie>? {

        val movieResponse = safeApiCall(
            call = {api.getFoundMovieAsync(query).await()},
            errorMessage = "Error Finding Movies"
        )
        return movieResponse?.results?.toMutableList()

    }

    suspend fun searchTvShowsInApi(query: String): MutableList<OneMovie>?{

        val tvResponse = safeApiCall(
            call = {api.getFoundTvShowAsync(query).await()},
            errorMessage = "Error finding tv shows"
        )
        return tvResponse?.results?.toMutableList()
    }



}