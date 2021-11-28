package com.example.cellcomtvassignment.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cellcomtvassignment.database.DatabaseHandler
import com.example.cellcomtvassignment.model.Movie
import com.example.cellcomtvassignment.model.MoviesRepository
import java.util.*

class FavoritesViewModel(application: Application) : AndroidViewModel(application){
    private val context by lazy { getApplication<Application>().applicationContext }
    private val databaseHandler : DatabaseHandler = DatabaseHandler(context)
    private lateinit var mMoviesRepository: MoviesRepository

    fun init(){
        mMoviesRepository = MoviesRepository.getInstance()!!
    }
   fun getFavoriteMovieList(): LiveData<LinkedList<Movie>> {

       val moviesList: LinkedList<Movie> = LinkedList<Movie>()
       for(movie in databaseHandler.getFavoritesList().value!!.values)
       {
           moviesList.add(movie)
       }
       val data : MutableLiveData<LinkedList<Movie>> = MutableLiveData<LinkedList<Movie>>()
       data.postValue(moviesList)
       return data
   }

}