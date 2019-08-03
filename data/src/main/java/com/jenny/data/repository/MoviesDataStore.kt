package com.jenny.data.repository

import com.jenny.domain.response.TopRatedMovieResponse
import io.reactivex.Single

public interface MoviesDataStore {
    fun getTopRatedMovies(pageNo: Int): Single<TopRatedMovieResponse>
}