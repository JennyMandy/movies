package com.jenny.data.repository

import com.jenny.domain.model.Movie
import io.reactivex.Completable
import io.reactivex.Single

interface MoviesCache {
    fun getSelectedMovie(movieId: Int): Single<Movie>

    fun setSelectedMovie(movie: Movie): Completable

    fun getFavouritedMovies(): Single<MutableList<Movie>>

    fun setFavouriteMovie(movie: Movie): Completable
}