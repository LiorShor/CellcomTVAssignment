package com.example.cellcomtvassignment.view.adapters

import com.example.cellcomtvassignment.model.Movie

interface OnMovieClickedListener {
    fun showMoviesDetailsFragment(movieId: Int?)
}