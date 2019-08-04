package com.jenny.cache.mapper

import com.jenny.cache.model.CachedMovie
import com.jenny.domain.model.Movie
import javax.inject.Inject

class CachedMovieMapper @Inject constructor() : Mapper<CachedMovie, Movie> {

    override fun mapToMovieModel(cacheModel: CachedMovie): Movie {
        return Movie(cacheModel.poster_path, cacheModel.adult, cacheModel.overview, cacheModel.release_date, null,
            cacheModel.id, cacheModel.original_title, cacheModel.original_language, cacheModel.title, cacheModel.backdrop_path,
            cacheModel.popularity, cacheModel.vote_count, cacheModel.video, cacheModel.vote_average, cacheModel.isShortlisted)
    }

    override fun mapToCachedMovie(cacheModel: Movie): CachedMovie {
        return CachedMovie(cacheModel.poster_path, cacheModel.adult, cacheModel.overview, cacheModel.release_date,
            cacheModel.id, cacheModel.original_title, cacheModel.original_language, cacheModel.title, cacheModel.backdrop_path,
            cacheModel.popularity, cacheModel.vote_count, cacheModel.video, cacheModel.vote_average, cacheModel.isShortlisted)
    }
}