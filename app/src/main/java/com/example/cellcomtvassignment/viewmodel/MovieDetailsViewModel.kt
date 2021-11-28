package com.example.cellcomtvassignment.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cellcomtvassignment.database.DatabaseHandler
import com.example.cellcomtvassignment.model.Movie
import com.example.cellcomtvassignment.model.MoviesRepository

class MovieDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val context by lazy { getApplication<Application>().applicationContext }
    private val databaseHandler: DatabaseHandler = DatabaseHandler(context)
    private lateinit var mMoviesRepository: MoviesRepository
    private var favoriteMoviesList = MutableLiveData<HashMap<Int, Movie>>()
    private var favoriteMovies = HashMap<Int, Movie>()
    fun init() {
        mMoviesRepository = MoviesRepository.getInstance()!!
        favoriteMoviesList = databaseHandler.getFavoritesList()
        favoriteMovies = databaseHandler.readMoviesFromDatabase()
    }

    fun getMovieById(id: Int): Movie {
        val movie : Movie = if (favoriteMovies[id] != null) {
            favoriteMovies[id]!!
        } else {
            mMoviesRepository.movies.findMovieById(id)
        }
        return movie
    }

    fun addMovieToFavorites(movie: Movie) {
        movie.id?.let { favoriteMovies.put(it, movie) }
        databaseHandler.addMovieToFavorites(movie)
    }

    fun removeMovieFromFavorites(movie: Movie) {
        favoriteMovies.remove(movie.id)
        movie.id?.let { databaseHandler.removeMovieFromFavorites(it) }
    }

    fun isInFavorite(movieId: Int): Boolean {
        return favoriteMovies.contains(movieId)
    }

    fun isFavoritesListChanged(): LiveData<Boolean> {
        return mMoviesRepository.isDataChanged
    }
}