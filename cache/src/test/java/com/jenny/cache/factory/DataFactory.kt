package com.jenny.domain.factory

import com.jenny.cache.model.CachedMovie
import com.jenny.domain.model.Movie
import com.jenny.domain.response.TopRatedMovieResponse
import java.util.*

object DataFactory {
    fun getRandomString(): String {
        return UUID.randomUUID().toString()
    }

    fun getRandomInt(): Int {
        return (Math.random() * 1000).toInt()
    }

    fun getRandomDouble(): Double {
        return Math.random()
    }

    fun getRandomBoolean(): Boolean {
        return Math.random() < 0.5
    }

    fun getRandomListOfInt(count: Int): List<Int> {
        val list = mutableListOf<Int>()
        repeat(count) {
            list.add(getRandomInt())
        }
        return list
    }

    fun getRandomMovie(): Movie {
        return Movie(
            getRandomString(),
            getRandomBoolean(),
            getRandomString(),
            getRandomString(),
            getRandomListOfInt(3),
            getRandomInt(),
            getRandomString(),
            getRandomString(),
            getRandomString(),
            getRandomString(),
            getRandomDouble(),
            getRandomInt(),
            getRandomBoolean(),
            getRandomDouble(),
            getRandomBoolean()
        )
    }

    fun getMovieList(count: Int): MutableList<Movie> {
        val results = mutableListOf<Movie>()
        repeat(count) {
            results.add(getRandomMovie())
        }
        return results
    }

    fun getRandomTopMovieList(): TopRatedMovieResponse {
        return TopRatedMovieResponse(
            getMovieList(10),
            getRandomInt(),
            getRandomInt(),
            getRandomInt(),
            getRandomBoolean(),
            getRandomString()
        )
    }
}