package com.jenny.domain.response

import com.jenny.domain.model.Movie

data class TopRatedMovieResponse(
    val results: List<Movie>,
    val page: Int,
    val total_results: Int,
    val total_pages: Int,
    val success: Boolean?,
    val status_message: String,
    val status_code: Int = 200
)
