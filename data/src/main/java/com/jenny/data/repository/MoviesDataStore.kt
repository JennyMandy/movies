package com.jenny.data.repository

import com.jenny.domain.model.Movie
import com.jenny.domain.response.TopRatedMovieResponse
import io.reactivex.Completable
import io.reactivex.Single

public interface MoviesDataStore {
    fun getTopRatedMovies(pageNo: Int): Single<TopRatedMovieResponse>

    fun getSelectedMovie(movieId: Int): Single<Movie>

    fun setSelectedMovie(movie: Movie): Completable

    fun getFavouritedMovies(): Single<MutableList<Movie>>

    fun setFavouriteMovie(movie: Movie): Completable
}