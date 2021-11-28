package com.example.cellcomtvassignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cellcomtvassignment.model.Movie
import com.example.cellcomtvassignment.model.MoviesRepository

class MoviesListViewModel : ViewModel() {
    var mMoviesList = MutableLiveData<List<Movie>>()
    private lateinit var mMoviesRepository: MoviesRepository
    fun init(){
        mMoviesRepository = MoviesRepository.getInstance()!!
        mMoviesList = mMoviesRepository.getMoviesList()
    }

    fun isDataChanged(): LiveData<Boolean?> {
        return mMoviesRepository.isDataChanged
    }

    fun nextPage() {
        mMoviesRepository.pageCounter++
        mMoviesRepository.getMoviesList()
    }
}