package com.kangaroo.filmore.Models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kangaroo.filmore.Utils.Constants.LOG_TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {

    var movies: MutableLiveData<List<OneMovie>>? = null

fun getDiscoverListMovie(): LiveData<List<OneMovie>> {
    val movies = MutableLiveData<List<OneMovie>>()
    val mService = Common.retrofitService
    mService.getMovieList("f845709366b2b2cd831e28f36ec9bb76", "ru").enqueue(object : Callback <Movie> {
        override fun onResponse(call: Call<Movie>,
                                response: Response<Movie>) {
            movies.value = response.body()?.results

        }
        override fun onFailure(call: Call<Movie>, t: Throwable) {
            Log.d(LOG_TAG, t.message ?: "Error from getDiscoverListMovie()_Repository.class")
            throw t

        }

    })
    return movies
}
//(key = "f845709366b2b2cd831e28f36ec9bb76", language = "ru",
//            movieId = movieId
    fun getMovieVideo(movieId: String): LiveData<List<VideoOneMovie>> {
        val movies = MutableLiveData<List<VideoOneMovie>>()
        val mService = Common.retrofitService
        mService.getMovieVideo(movieId, "f845709366b2b2cd831e28f36ec9bb76", "ru")
            .enqueue(object : Callback <VideoMovie> {
            override fun onResponse(call: Call<VideoMovie>,
                                    response: Response<VideoMovie>) {
                movies.value = response.body()?.results

            }
            override fun onFailure(call: Call<VideoMovie>, t: Throwable) {
                Log.d(LOG_TAG, t.message ?: "Error from getDiscoverListMovie()_Repository.class")
                throw t

            }

        })
        return movies
    }

    fun getSearchedData(query: String){
        movies = MutableLiveData()
        val mService = Common.retrofitService
        mService.getSearchedData("f845709366b2b2cd831e28f36ec9bb76", "ru", query).enqueue(object :
            Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
               movies?.value = response.body()?.results
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Log.d(LOG_TAG, t.message ?: "Error from getDiscoverListMovie()_Repository.class")
                throw t
            }

        } )
    }



}


object Common {
    private val BASE_URL = "https://api.themoviedb.org/3/"
    val retrofitService: ApiTMDB
        get() = RetrofitClient.getClient(BASE_URL).create(ApiTMDB::class.java)
}