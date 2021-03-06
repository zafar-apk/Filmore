package com.kangaroo.filmore.Models

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.kangaroo.filmore.BuildConfig
import com.kangaroo.filmore.Utils.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body


//ApiFactory to create TMDB Api
object ApiFactory{

    //Creating Auth Interceptor to add api_key and language query in front of all the requests.
    private val authInterceptor = Interceptor { chain->
        val newUrl = chain.request().url
            .newBuilder()
            .addQueryParameter("api_key", Constants.tmdbApiKey)
            .addQueryParameter("language", Constants.apiLanguage)
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }

    private val logInterceptor = HttpLoggingInterceptor()


    //OkhttpClient for building http request url
    private val tmdbClient = OkHttpClient().newBuilder()
        .addInterceptor(authInterceptor)
        .addInterceptor(logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()



    fun retrofit() : Retrofit = Retrofit.Builder()
        .client(tmdbClient)
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()


    val tmdbApi : ApiTMDB = retrofit().create(ApiTMDB::class.java)

}
