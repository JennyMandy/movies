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
class GetSelectedMoviesTest {
    @Mock
    private lateinit var postExecutionThread: PostExecutionThread

    @Mock
    private lateinit var moviesRepository: MoviesRepository

    private lateinit var getSelectedMovies: GetSelectedMovies

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getSelectedMovies = GetSelectedMovies(moviesRepository, postExecutionThread)
    }

    private fun stubGetSelectedMovies(single: Single<Movie>, movieId: Int) {
        Mockito.`when`(moviesRepository.getSelectedMovie(movieId)).thenReturn(single)
    }

    @Test
    fun getSelectedMoviesCompletesTest() {
        val movieId = DataFactory.getRandomInt()
        val topRatedMovieResponse = DataFactory.getRandomMovie()
        stubGetSelectedMovies(Single.just(topRatedMovieResponse), movieId)
        val testObserver = getSelectedMovies.buildUseCaseObservable(GetSelectedMovies.Params.getParams(movieId)).test()
        testObserver.assertComplete()
    }

    @Test
    fun getSelectedMoviesReturnsDataTest() {
        val movieId = DataFactory.getRandomInt()
        val topRatedMovieResponse = DataFactory.getRandomMovie()
        stubGetSelectedMovies(Single.just(topRatedMovieResponse), movieId)
        val testObserver = getSelectedMovies.buildUseCaseObservable(GetSelectedMovies.Params.getParams(movieId)).test()
        testObserver.assertValue(topRatedMovieResponse)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getSelectedMoviesThrowsErrorIfMovieIdIsZeroTest() {
        val movieId = 0
        val topRatedMovieResponse = DataFactory.getRandomMovie()
        stubGetSelectedMovies(Single.just(topRatedMovieResponse), movieId)
        getSelectedMovies.buildUseCaseObservable(GetSelectedMovies.Params.getParams(movieId)).test()
    }

    @Test(expected = IllegalArgumentException::class)
    fun getSelectedMoviesThrowsErrorIfParamsIsNullTest() {
        val movieId = 0
        val topRatedMovieResponse = DataFactory.getRandomMovie()
        stubGetSelectedMovies(Single.just(topRatedMovieResponse), movieId)
        getSelectedMovies.buildUseCaseObservable(null).test()
    }

    @Test(expected = IllegalArgumentException::class)
    fun getSelectedMoviesThrowsErrorIfObserverIsNullTest() {
        getSelectedMovies = GetSelectedMovies(moviesRepository, postExecutionThread)
        getSelectedMovies.execute(null)
    }
}