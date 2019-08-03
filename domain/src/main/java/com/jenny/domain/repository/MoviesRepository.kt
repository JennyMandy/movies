package com.jenny.domain.repository

import com.jenny.domain.response.TopRatedMovieResponse
import io.reactivex.Single

interface MoviesRepository {
    fun getTopRatedMovies(pageNo: Int): Single<TopRatedMovieResponse>
}