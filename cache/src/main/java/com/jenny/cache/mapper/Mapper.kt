package com.jenny.cache.mapper

interface Mapper<C, E> {
    fun mapToMovieModel(cacheModel: C): E
    fun mapToCachedMovie(movie: E): C
}