package com.jenny.data.impl

import com.jenny.data.store.MoviesDataStoreFactory
import com.jenny.domain.model.Movie
import com.jenny.domain.repository.MoviesRepository
import com.jenny.domain.response.TopRatedMovieResponse
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class MoviesDataRepositoryImpl @Inject
constructor(
    private val moviesDataStoreFactory: MoviesDataStoreFactory
) : MoviesRepository {
    override fun getTopRatedMovies(pageNo: Int): Single<TopRatedMovieResponse> {
        return moviesDataStoreFactory.moviesRemoteDataStore.getTopRatedMovies(pageNo)
    }

    override fun getSelectedMovie(movieId: Int): Single<Movie> {
        return moviesDataStoreFactory.moviesCacheDataStore.getSelectedMovie(movieId)
    }

    override fun setSelectedMovie(movie: Movie): Completable {
        return moviesDataStoreFactory.moviesCacheDataStore.setSelectedMovie(movie)
    }

    override fun getFavouritedMovies(): Single<MutableList<Movie>> {
        return moviesDataStoreFactory.moviesCacheDataStore.getFavouritedMovies()
    }

    override fun setFavouriteMovie(movie: Movie): Completable {
        return moviesDataStoreFactory.moviesCacheDataStore.setFavouriteMovie(movie)
    }
}