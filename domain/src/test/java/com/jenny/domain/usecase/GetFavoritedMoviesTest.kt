package com.jenny.domain.usecase

import com.jenny.domain.executor.PostExecutionThread
import com.jenny.domain.factory.DataFactory
import com.jenny.domain.model.Movie
import com.jenny.domain.repository.MoviesRepository
import com.jenny.domain.response.TopRatedMovieResponse
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class GetFavoritedMoviesTest {
    @Mock
    private lateinit var postExecutionThread: PostExecutionThread

    @Mock
    private lateinit var moviesRepository: MoviesRepository

    private lateinit var getFavouritedMovies: GetFavouritedMovies

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getFavouritedMovies = GetFavouritedMovies(moviesRepository, postExecutionThread)
    }

    private fun stubGetFavouritedMovies(single: Single<MutableList<Movie>>) {
        Mockito.`when`(moviesRepository.getFavouritedMovies()).thenReturn(single)
    }

    @Test
    fun getTopRatedMoviesCompletesTest() {
        val movieList = DataFactory.getMovieList(5)
        stubGetFavouritedMovies(Single.just(movieList))
        val testObserver = getFavouritedMovies.buildUseCaseObservable().test()
        testObserver.assertComplete()
    }

    @Test
    fun getTopRatedMoviesReturnsDataTest() {
        val movieList = DataFactory.getMovieList(5)
        stubGetFavouritedMovies(Single.just(movieList))
        val testObserver = getFavouritedMovies.buildUseCaseObservable().test()
        testObserver.assertValue(movieList)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getTopRatedMoviesThrowsErrorIfObserverIsNullTest() {
        getFavouritedMovies = GetFavouritedMovies(moviesRepository, postExecutionThread)
        getFavouritedMovies.execute(null)
    }
}