package com.jenny.domain.model

data class Movie(
    val poster_path: String? = null,
    val adult: Boolean = false,
    val overview: String? = null,
    val release_date: String? = null,
    val genre_ids: List<Int>? = null,
    val id: Int = 0,
    val original_title: String? = null,
    val original_language: String? = null,
    val title: String? = null,
    val backdrop_path: String? = null,
    val popularity: Double = 0.0,
    val vote_count: Int = 0,
    val video: Boolean = false,
    val vote_average: Double = 0.0,
    val isShortlisted: Boolean = false
)