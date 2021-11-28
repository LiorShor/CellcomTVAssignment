package com.example.cellcomtvassignment.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cellcomtvassignment.model.Movie
import com.example.cellcomtvassignment.model.Movies
import com.example.cellcomtvassignment.model.MoviesRepository
import java.util.*

class MovieDetailsViewModel : ViewModel(){
    private lateinit var mMoviesRepository:MoviesRepository
    var favoriteMovies = HashSet<Int>()
    fun init(){
        mMoviesRepository = MoviesRepository.getInstance()!!
    }

    fun getMovieById(id : Int) : Movie
    {
        return mMoviesRepository.movies.findMovieById(id)
    }
    fun addMovieToFavorites(movieId : Int)
    {
        favoriteMovies.add(movieId)
    }

    fun removeMovieFromFavorites(movieId : Int){
        favoriteMovies.remove(movieId)
    }

    fun isInFavorite(movieId: Int) : Boolean
    {
        return favoriteMovies.contains(movieId)
    }
}