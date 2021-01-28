package com.kangaroo.filmore.Models

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiTMDB {
    //Получение списка фильмов -> discover
    @GET("discover/movie")
    fun getMovieList(@Query("api_key") key: String, @Query("language") language: String): Call<Movie>

    //Получение списка трейлеров фильма -> Youtube
    @GET("movie/{movieID}/videos")
    fun getMovieVideo(@Path("movieID") movieId :String, @Query("api_key") key: String,
    @Query("language") language: String): Call<VideoMovie>

    //Поиск -> Query
    @GET("search/multi")
    fun getSearchedData(
        @Query("api_key") key: String,
        @Query("language") language: String,
        @Query("query") query: String,
    ): Call<Movie>


}