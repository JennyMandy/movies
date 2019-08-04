package com.jenny.cache.impl

import com.jenny.cache.db.MovieDB
import com.jenny.cache.mapper.CachedMovieMapper
import com.jenny.cache.sharedpreferences.MovieSharedPreferences
import com.jenny.data.repository.MoviesCache
import com.jenny.domain.model.Movie
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class MovieCacheImpl @Inject
constructor(
    private val sharedPreferences: MovieSharedPreferences,
    private val movieDB: MovieDB,
    private val mapper: CachedMovieMapper
) : MoviesCache {
    override fun getSelectedMovie(movieId: Int): Single<Movie> {
        return Single.defer {
            var movie = sharedPreferences.getSelectedMovie
            movie?.let {
                if (movieId != movie?.id) {
                    movie = Movie()
                }
            }
            Single.just(movie)
        }
    }

    override fun setSelectedMovie(movie: Movie): Completable {
        return Completable.defer {
            sharedPreferences.saveSelectedMovie(movie)
            Completable.complete()
        }
    }

    override fun getFavouritedMovies(): Single<MutableList<Movie>> {
        return movieDB.movieDao().getAllMovies().map {
            it.map {
                mapper.mapToMovieModel(it)
            } as MutableList<Movie>
        }
    }

    override fun setFavouriteMovie(movie: Movie): Completable {
        return Completable.defer {
            movieDB.movieDao().insertMovie(mapper.mapToCachedMovie(movie))
            Completable.complete()
        }
    }
}