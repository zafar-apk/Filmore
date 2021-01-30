package com.kangaroo.filmore.Models

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiTMDB {

    //Получение списка фильмов -> discover
    @GET("discover/movie")
    fun getMovieList(): Deferred<Response<Movie>>

    //Получение списка трейлеров фильма -> Youtube
    @GET("movie/{movieID}/videos")
    fun getMovieVideo(@Path("movieID") movieId :String): Deferred<Response<VideoMovie>>

    //Поиск -> Query
    @GET("search/multi")
    fun getSearchedData(
        @Query("query") query: String,
    ): Deferred<Response<Movie>>


}