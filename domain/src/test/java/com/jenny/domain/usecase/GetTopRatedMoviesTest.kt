package com.jenny.domain.usecase

import com.jenny.domain.executor.PostExecutionThread
import com.jenny.domain.factory.DataFactory
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
class GetTopRatedMoviesTest {
    @Mock
    private lateinit var postExecutionThread: PostExecutionThread

    @Mock
    private lateinit var moviesRepository: MoviesRepository

    private lateinit var getTopRatedMovies: GetTopRatedMovies

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getTopRatedMovies = GetTopRatedMovies(moviesRepository, postExecutionThread)
    }

    private fun stubGetTopRatedMovies(single: Single<TopRatedMovieResponse>, pageNo: Int) {
        Mockito.`when`(moviesRepository.getTopRatedMovies(pageNo)).thenReturn(single)
    }

    @Test
    fun getTopRatedMoviesCompletesTest() {
        val pageNo = DataFactory.getRandomInt()
        val topRatedMovieResponse = DataFactory.getRandomTopMovieList()
        stubGetTopRatedMovies(Single.just(topRatedMovieResponse), pageNo)
        val testObserver = getTopRatedMovies.buildUseCaseObservable(GetTopRatedMovies.Params.getParams(pageNo)).test()
        testObserver.assertComplete()
    }

    @Test
    fun getTopRatedMoviesReturnsDataTest() {
        val pageNo = DataFactory.getRandomInt()
        val topRatedMovieResponse = DataFactory.getRandomTopMovieList()
        stubGetTopRatedMovies(Single.just(topRatedMovieResponse), pageNo)
        val testObserver = getTopRatedMovies.buildUseCaseObservable(GetTopRatedMovies.Params.getParams(pageNo)).test()
        testObserver.assertValue(topRatedMovieResponse)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getTopRatedMoviesThrowsErrorIfPageNoIsZeroTest() {
        val pageNo = 0
        val topRatedMovieResponse = DataFactory.getRandomTopMovieList()
        stubGetTopRatedMovies(Single.just(topRatedMovieResponse), pageNo)
        getTopRatedMovies.buildUseCaseObservable(GetTopRatedMovies.Params.getParams(pageNo)).test()
    }

    @Test(expected = IllegalArgumentException::class)
    fun getTopRatedMoviesThrowsErrorIfParamsIsNullTest() {
        val pageNo = 0
        val topRatedMovieResponse = DataFactory.getRandomTopMovieList()
        stubGetTopRatedMovies(Single.just(topRatedMovieResponse), pageNo)
        getTopRatedMovies.buildUseCaseObservable(null).test()
    }

    @Test(expected = IllegalArgumentException::class)
    fun getTopRatedMoviesThrowsErrorIfObserverIsNullTest() {
        getTopRatedMovies = GetTopRatedMovies(moviesRepository, postExecutionThread)
        getTopRatedMovies.execute(null)
    }
}