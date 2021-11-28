package com.example.cellcomtvassignment.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.cellcomtvassignment.R
import com.example.cellcomtvassignment.databinding.ActivityMoviesListBinding
import com.example.cellcomtvassignment.view.adapters.OnMovieClickedListener
import com.example.cellcomtvassignment.view.fragments.MovieDetails.Companion.MOVIE_ID

class MoviesMainActivity : AppCompatActivity(), OnMovieClickedListener {
    private lateinit var mBindingMoviesMainActivity: ActivityMoviesListBinding
    private lateinit var navController: NavController
    var isSignInFragment = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBindingMoviesMainActivity = ActivityMoviesListBinding.inflate(layoutInflater)
        setContentView(mBindingMoviesMainActivity.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.moviesContainer) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(
            mBindingMoviesMainActivity.bottomNavigationView,
            navController
        )
    }

    fun showMoviesListFragment() {
        onBackPressed()
    }

    override fun onBackPressed() {
        if (isSignInFragment) {
            ActivityCompat.finishAffinity(this)
            finish()
        } else {
            super.onBackPressed()
            isSignInFragment = true
        }
    }

    override fun showMoviesDetailsFragment(movieId: Int?) {
        val bundle = Bundle()
        movieId?.let { bundle.putInt(MOVIE_ID, it) }
        navController.navigate(R.id.movieDetailsFragment,bundle)
        isSignInFragment = false

    }
}