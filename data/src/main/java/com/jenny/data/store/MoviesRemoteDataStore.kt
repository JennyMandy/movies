package com.jenny.data.store

import com.jenny.data.ApplicationExceptions
import com.jenny.data.repository.MoviesDataStore
import com.jenny.data.repository.MoviesRemote
import com.jenny.domain.model.Movie
import com.jenny.domain.response.TopRatedMovieResponse
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class MoviesRemoteDataStore @Inject constructor(private val moviesRemote: MoviesRemote) : MoviesDataStore {
    override fun getTopRatedMovies(pageNo: Int): Single<TopRatedMovieResponse> {
        return moviesRemote.getTopRatedMovies(pageNo)
    }

    override fun getSearchResults(pageNo: Int, query: String): Single<TopRatedMovieResponse> {
        return moviesRemote.getSearchResults(pageNo, query)
    }

    override fun getSelectedMovie(movieId: Int): Single<Movie> {
        throw IllegalArgumentException(ApplicationExceptions.NO_REMOTE_SUPPORT)
    }

    override fun setSelectedMovie(movie: Movie): Completable {
        throw IllegalArgumentException(ApplicationExceptions.NO_REMOTE_SUPPORT)
    }

    override fun getFavouritedMovies(): Single<MutableList<Movie>> {
        throw IllegalArgumentException(ApplicationExceptions.NO_REMOTE_SUPPORT)
    }

    override fun setFavouriteMovie(movie: Movie): Completable {
        throw IllegalArgumentException(ApplicationExceptions.NO_REMOTE_SUPPORT)
    }
}