package com.kangaroo.filmore.Models

import android.util.Log
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.*

class MoviePageKeyedDataSource(
    private val repository: MovieRepository,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, OneMovie>() {

    // FOR DATA ---
    private var supervisorJob = SupervisorJob()
    //...

    // OVERRIDE ---
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, OneMovie>
    ) {
        // ...
        executeQuery(1) {
            callback.onResult(it, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, OneMovie>) {
        val page = params.key
        // ...
        executeQuery(page) {
            callback.onResult(it, page + 1)
        }
    }

    override fun invalidate() {
        super.invalidate()
        supervisorJob.cancelChildren()   // Cancel possible running job to only keep last result searched by user
    }

    // UTILS ---
    private fun executeQuery(page: Int, callback: (List<OneMovie>) -> Unit) {
        // ...
        scope.launch(getJobErrorHandler() + supervisorJob) {
            val movies = repository.getPopularMovies(page)
            // ...
            if (movies != null) {
                callback(movies)
            }
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        Log.e(MoviePageKeyedDataSource::class.java.simpleName, "An error happened: $e")

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, OneMovie>) {
        TODO("Not yet implemented")
    }


}

class MovieDataSourceFactory(
    private val repository: MovieRepository,
    private val scope: CoroutineScope
) :
    DataSource.Factory<Int, OneMovie>() {
    override fun create(): DataSource<Int, OneMovie> {
       return MoviePageKeyedDataSource(repository, scope)
    }
}