package com.example.cellcomtvassignment.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cellcomtvassignment.databinding.MovieItemBinding
import com.example.cellcomtvassignment.model.Movie
import com.example.cellcomtvassignment.model.Movies
import java.util.*

class MoviesAdapter(private val mOnMovieClickedListener: OnMovieClickedListener, private val mContext : Context) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private val mMoviesList : LinkedList<Movie> = LinkedList<Movie>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), mOnMovieClickedListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = mMoviesList[position]
        Glide.with(mContext)
            .load("https://image.tmdb.org/t/p/original/${movie.posterPath}")
            .into(holder.mMovieItemBinding.movieImageView)
    }

    override fun getItemCount(): Int {
        return mMoviesList.size
    }

    inner class ViewHolder(
        movieItemBinding: MovieItemBinding,
        private var onMovieClickedListener: OnMovieClickedListener
    ) :
        RecyclerView.ViewHolder(movieItemBinding.root), View.OnClickListener {
        val mMovieItemBinding: MovieItemBinding = movieItemBinding

        override fun onClick(view: View) {
            onMovieClickedListener.showMoviesDetailsFragment(mMoviesList[adapterPosition].id)
        }

        init {
            mMovieItemBinding.root.setOnClickListener(this)
        }
    }

    fun setStatusesList(movies: List<Movie>) {
        mMoviesList.addAll(movies)
        notifyDataSetChanged()
    }

}