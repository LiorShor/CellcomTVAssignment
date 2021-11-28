package com.example.cellcomtvassignment.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.cellcomtvassignment.R
import com.example.cellcomtvassignment.databinding.ActivityMoviesListBinding
import com.example.cellcomtvassignment.model.Movie
import com.example.cellcomtvassignment.view.adapters.OnMovieClickedListener
import com.example.cellcomtvassignment.view.fragments.MovieDetails
import com.example.cellcomtvassignment.view.fragments.MovieDetails.Companion.MOVIE_ID
import com.example.cellcomtvassignment.view.fragments.MoviesListFragment

class MoviesMainActivity : AppCompatActivity(), OnMovieClickedListener {
    private lateinit var mBindingMoviesMainActivity: ActivityMoviesListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBindingMoviesMainActivity = ActivityMoviesListBinding.inflate(layoutInflater)
        setContentView(mBindingMoviesMainActivity.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.moviesContainer) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(
            mBindingMoviesMainActivity.bottomNavigationView,
            navController
        )
    }

    fun showMoviesListFragment() {
        onBackPressed()
    }

    override fun showMoviesDetailsFragment(movieId: Int?) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragment = MovieDetails()
        val bundle = Bundle()
        movieId?.let { bundle.putInt(MOVIE_ID, it) }
        fragment.arguments = bundle
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.moviesContainer, fragment).addToBackStack(null).commit()
    }
}