package com.jenny.data.store

import com.jenny.data.ApplicationExceptions
import com.jenny.data.repository.MoviesCache
import com.jenny.data.repository.MoviesDataStore
import com.jenny.domain.model.Movie
import com.jenny.domain.response.TopRatedMovieResponse
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class MoviesCacheDataStore @Inject constructor(private val moviesCache: MoviesCache) : MoviesDataStore {
    override fun getTopRatedMovies(pageNo: Int): Single<TopRatedMovieResponse> {
        throw IllegalArgumentException(ApplicationExceptions.NO_CACHE_SUPPORT)
    }

    override fun getSelectedMovie(movieId: Int): Single<Movie> {
        return moviesCache.getSelectedMovie(movieId)
    }

    override fun setSelectedMovie(movie: Movie): Completable {
        return moviesCache.setSelectedMovie(movie)
    }

    override fun getFavouritedMovies(): Single<MutableList<Movie>> {
        return moviesCache.getFavouritedMovies()
    }

    override fun setFavouriteMovie(movie: Movie): Completable {
        return moviesCache.setFavouriteMovie(movie)
    }
}