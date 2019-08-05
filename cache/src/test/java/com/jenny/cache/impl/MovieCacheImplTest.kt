package com.jenny.cache.impl

import com.jenny.cache.db.MovieDB
import com.jenny.cache.mapper.CachedMovieMapper
import com.jenny.cache.sharedpreferences.MovieSharedPreferences
import com.jenny.domain.factory.DataFactory
import com.jenny.domain.model.Movie
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class MovieCacheImplTest {
    private val movieSharedPreferences = Mockito.mock(MovieSharedPreferences::class.java)
    private val movieDB = Mockito.mock(MovieDB::class.java)
    private val cachedMovieMapper = Mockito.mock(CachedMovieMapper::class.java)
    private val cache = MovieCacheImpl(movieSharedPreferences, movieDB, cachedMovieMapper)

    private fun stubGetSelectedMovie(movie: Movie) {
        Mockito.`when`(movieSharedPreferences.getSelectedMovie).thenReturn(movie)
    }

    private fun stubSetSelectedMovie(movie: Movie, success: Boolean) {
        Mockito.`when`(movieSharedPreferences.saveSelectedMovie(movie)).thenReturn(success)
    }

    @Test
    fun getSelectedMovieCompletesTest() {
        val movie = DataFactory.getRandomMovie()
        stubGetSelectedMovie(movie)
        val testObserver = cache.getSelectedMovie(1).test()
        testObserver.assertComplete()
    }

    @Test
    fun setSelectedMovieCompletesTest() {
        val movie = DataFactory.getRandomMovie()
        stubSetSelectedMovie(movie, true)
        val testObserver = cache.setSelectedMovie(movie).test()
        testObserver.assertComplete()
    }
}