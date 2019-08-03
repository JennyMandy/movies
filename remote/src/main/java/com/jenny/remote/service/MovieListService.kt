package com.jenny.remote.service

import com.jenny.domain.response.TopRatedMovieResponse
import com.jenny.remote.constants.Constants
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieListService {
    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Single<TopRatedMovieResponse>
}