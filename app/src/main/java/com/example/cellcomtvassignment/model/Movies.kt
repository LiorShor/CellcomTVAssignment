package com.example.cellcomtvassignment.model

class Movies {
    private var mMoviesMap = HashMap<Int?, Movie>()

    fun addListOfMoviesToMap(moviesList : List<Movie>?)
    {
        if (moviesList != null) {
            for (movie in moviesList)
                addMovieToMap(movie)
        }
    }

    private fun addMovieToMap(movie: Movie) {
        mMoviesMap[movie.id] = movie
    }

    fun findMovieById(movieId: Int?): Movie {
        return mMoviesMap.filterKeys { i: Int? -> i == movieId }.values.first()
    }

}