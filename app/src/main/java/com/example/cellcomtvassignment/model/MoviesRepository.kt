package com.example.cellcomtvassignment.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.cellcomtvassignment.view.fragments.MoviesListFragment.Companion.POPULAR
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class MoviesRepository {

    val movies = Movies()
    private var currentCategory: String = POPULAR
    var pageCounter = 1
    val isDataChanged: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    var moviesList: LinkedList<Movie>? = LinkedList()

    companion object {
        private var mInstance: MoviesRepository? = null

        @Synchronized
        fun getInstance(): MoviesRepository? {
            if (mInstance == null) // if instance is not created before
            {
                mInstance = MoviesRepository()
            }
            return mInstance
        }

        private const val TAG = "Error"
    }

    private fun fetchAllMoviesFromApi(category: String) {
        moviesList?.clear()
        val apiInterface = MoviesApiService()
        GlobalScope.launch(Dispatchers.Main) {
            try {
                if (currentCategory != category) {
                    pageCounter = 1
                    currentCategory = category
                }
                val movieResponse: MovieResponse = when (category) {
                    "Popular" -> {
                        apiInterface.getPopularMoviesAsync(pageCounter).await()
                    }
                    "Latest" -> {
                        apiInterface.getLatestMoviesAsync(pageCounter).await()
                    }
                    else -> {
                        apiInterface.getUpcomingMoviesAsync(pageCounter).await()
                    }
                }
                movies.addListOfMoviesToMap(movieResponse.movies)
                movieResponse.movies?.let { moviesList?.addAll(it) }
                isDataChanged.postValue(true)
            } catch (exception: Exception) {
                Log.d(TAG, exception.toString())
            }
        }
    }

    fun getMoviesList(category: String): MutableLiveData<List<Movie>> {
        fetchAllMoviesFromApi(category)
        val data: MutableLiveData<List<Movie>> = MutableLiveData<List<Movie>>()
        data.value = moviesList
        return data
    }
}