package com.jenny.remote.impl

import com.jenny.data.repository.MoviesRemote
import com.jenny.domain.response.TopRatedMovieResponse
import com.jenny.remote.constants.Constants
import com.jenny.remote.service.MovieListService
import io.reactivex.Single
import javax.inject.Inject

class MoviesListImpl @Inject
constructor(private val movieListService: MovieListService) : MoviesRemote {
    override fun getTopRatedMovies(pageNo: Int): Single<TopRatedMovieResponse> {
        return movieListService.getTopRatedMovies(Constants.API_KEY, pageNo)
    }
    override fun getSearchResults(pageNo: Int, query: String): Single<TopRatedMovieResponse> {
        return movieListService.getSearchResults(Constants.API_KEY, pageNo, query)
    }
}