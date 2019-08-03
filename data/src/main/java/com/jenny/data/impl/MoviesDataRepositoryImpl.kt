package com.jenny.data.impl

import com.jenny.data.store.MoviesDataStoreFactory
import com.jenny.domain.repository.MoviesRepository
import com.jenny.domain.response.TopRatedMovieResponse
import io.reactivex.Single
import javax.inject.Inject

class MoviesDataRepositoryImpl @Inject
constructor(private val moviesDataStoreFactory: MoviesDataStoreFactory) : MoviesRepository {
    override fun getTopRatedMovies(pageNo: Int): Single<TopRatedMovieResponse> {
        return moviesDataStoreFactory.getDataStore(false).getTopRatedMovies(pageNo)
    }
}