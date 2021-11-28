package com.example.cellcomtvassignment.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.cellcomtvassignment.R
import com.example.cellcomtvassignment.databinding.FragmentMovieDetailsBinding
import com.example.cellcomtvassignment.viewmodel.MovieDetailsViewModel
import android.text.method.ScrollingMovementMethod


class MovieDetails : Fragment() {
    private lateinit var mFragmentMovieDetailsBinding: FragmentMovieDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mFragmentMovieDetailsBinding = FragmentMovieDetailsBinding.inflate(inflater, null, false)
        return mFragmentMovieDetailsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieDetailsViewModel: MovieDetailsViewModel =
            ViewModelProvider(this)[MovieDetailsViewModel::class.java]
        movieDetailsViewModel.init()

        val movie = arguments?.getInt(MOVIE_ID)?.let { movieDetailsViewModel.getMovieById(it) }
        if (movie != null) {
            mFragmentMovieDetailsBinding.movieDescription.movementMethod = ScrollingMovementMethod()
            mFragmentMovieDetailsBinding.movieTitle.text = movie.title
            mFragmentMovieDetailsBinding.movieDescription.text = movie.overview
            context?.let {
                Glide.with(it)
                    .load("https://image.tmdb.org/t/p/original/${movie.posterPath}")
                    .into(mFragmentMovieDetailsBinding.movieImageView)
            }
            if (movie.id?.let { it1 -> movieDetailsViewModel.isInFavorite(it1) } == true) {
                mFragmentMovieDetailsBinding.backButton.setBackgroundResource(R.drawable.ic_full_star)
            }
        }
        mFragmentMovieDetailsBinding.backButton.setOnClickListener {
            if (movie?.id?.let { it1 -> movieDetailsViewModel.isInFavorite(it1) } == false) {
                mFragmentMovieDetailsBinding.backButton.setBackgroundResource(R.drawable.ic_empty_star)
            } else {
                mFragmentMovieDetailsBinding.backButton.setBackgroundResource(R.drawable.ic_full_star)
            }
        }
    }

    companion object {
        const val MOVIE_ID = "MovieId"
    }
}