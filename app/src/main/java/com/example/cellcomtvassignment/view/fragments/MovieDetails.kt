package com.example.cellcomtvassignment.view.fragments

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.cellcomtvassignment.R
import com.example.cellcomtvassignment.databinding.FragmentMovieDetailsBinding
import com.example.cellcomtvassignment.view.activities.MoviesMainActivity
import com.example.cellcomtvassignment.viewmodel.MovieDetailsViewModel


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
            movieDetailsViewModel.isFavoritesListChanged()
                .observe(viewLifecycleOwner, { t ->
                    if (t)
                        if (movie.id?.let { it1 -> movieDetailsViewModel.isInFavorite(it1) } == true) {
                            mFragmentMovieDetailsBinding.favoritesImageButton.setImageResource(R.drawable.ic_full_star)
                        }
                })

        }
        mFragmentMovieDetailsBinding.favoritesImageButton.setOnClickListener {
            if (movie?.id?.let { it1 -> movieDetailsViewModel.isInFavorite(it1) } == false) {
                mFragmentMovieDetailsBinding.favoritesImageButton.setImageResource(R.drawable.ic_full_star)
                movie.id.let { movieDetailsViewModel.addMovieToFavorites(movie) }
            } else {
                mFragmentMovieDetailsBinding.favoritesImageButton.setImageResource(R.drawable.ic_empty_star)
                movie?.id?.let { movieDetailsViewModel.removeMovieFromFavorites(movie) }
            }
        }
        mFragmentMovieDetailsBinding.backButton.setOnClickListener {
            (activity as MoviesMainActivity).showMoviesListFragment()
        }
    }

    companion object {
        const val MOVIE_ID = "MovieId"
    }
}