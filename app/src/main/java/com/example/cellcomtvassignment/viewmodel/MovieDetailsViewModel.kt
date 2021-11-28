package com.example.cellcomtvassignment.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cellcomtvassignment.database.DatabaseHandler
import com.example.cellcomtvassignment.model.Movie
import com.example.cellcomtvassignment.model.MoviesRepository

class MovieDetailsViewModel(application: Application) : AndroidViewModel(application){
    private val context by lazy { getApplication<Application>().applicationContext }
    private val databaseHandler : DatabaseHandler = DatabaseHandler(context)
    private lateinit var mMoviesRepository:MoviesRepository
    private var favoriteMoviesList = MutableLiveData<Set<Int>>()
    private var favoriteMovies=HashSet<Int>()
    fun init(){
        mMoviesRepository = MoviesRepository.getInstance()!!
        favoriteMoviesList = databaseHandler.getFavoritesList()
        favoriteMovies = databaseHandler.readMoviesFromDatabase() as HashSet<Int>
    }

    fun getMovieById(id : Int) : Movie
    {
        return mMoviesRepository.movies.findMovieById(id)
    }
    fun addMovieToFavorites(movieId : Int)
    {
        favoriteMovies.add(movieId)
        databaseHandler.addMovieToFavorites(movieId)
    }

    fun removeMovieFromFavorites(movieId : Int){
        favoriteMovies.remove(movieId)
        databaseHandler.removeMovieFromFavorites(movieId)
    }

    fun isInFavorite(movieId: Int) : Boolean
    {
        return favoriteMovies.contains(movieId)
    }
    fun isFavoritesListChanged(): LiveData<Boolean> {
        return mMoviesRepository.isDataChanged
    }
}