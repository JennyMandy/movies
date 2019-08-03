package com.jenny.data.store

import com.jenny.data.repository.MoviesDataStore
import com.jenny.data.repository.MoviesRemote
import com.jenny.domain.response.TopRatedMovieResponse
import io.reactivex.Single
import javax.inject.Inject

class MoviesRemoteDataStore @Inject constructor(private val moviesRemote: MoviesRemote) : MoviesDataStore {
    override fun getTopRatedMovies(pageNo: Int): Single<TopRatedMovieResponse> {
        return moviesRemote.getTopRatedMovies(pageNo)
    }

}