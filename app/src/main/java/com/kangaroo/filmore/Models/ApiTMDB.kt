package com.kangaroo.filmore.Models

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiTMDB {

    //Получение списка фильмов -> discover
    @GET("discover/movie")
    fun getMovieListAsync(
        @Query("page") page: Int
    ): Deferred<Response<Movie>>

    //Получение списка трейлеров фильма -> Youtube
    @GET("movie/{movieID}/videos")
    fun getMovieVideoAsync(
        @Path("movieID") movieId: String
    ): Deferred<Response<VideoMovie>>

    //Поиск -> Query
    @GET("search/movie")
    fun getFoundMovieAsync(
        @Query("query") query: String,
    ): Deferred<Response<Movie>>

    @GET("search/tv")
    fun getFoundTvShowAsync(
        @Query("query") query: String,
    ): Deferred<Response<Movie>>

    //Получение списка trending -> home fragment
    @GET("trending/{media_type}/{time_window}")
    fun getTrendingListAsync(
        @Path("media_type") media_type: String,
        @Path("time_window") time_window: String,
        @Query("page") page: Int
    ): Deferred<Response<Movie>>

}