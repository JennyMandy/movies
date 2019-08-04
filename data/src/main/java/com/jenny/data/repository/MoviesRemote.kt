package com.jenny.data.repository

import com.jenny.domain.response.TopRatedMovieResponse
import io.reactivex.Single

interface MoviesRemote {
    fun getTopRatedMovies(pageNo: Int): Single<TopRatedMovieResponse>
    fun getSearchResults(pageNo: Int, query: String): Single<TopRatedMovieResponse>
}