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
class GetSearchedMoviesTest {
    @Mock
    private lateinit var postExecutionThread: PostExecutionThread

    @Mock
    private lateinit var moviesRepository: MoviesRepository

    private lateinit var getSearchedMovies: GetSearchedMovies

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getSearchedMovies = GetSearchedMovies(moviesRepository, postExecutionThread)
    }

    private fun stubGetSearchedMovies(single: Single<TopRatedMovieResponse>, pageNo: Int, query: String) {
        Mockito.`when`(moviesRepository.getSearchResults(pageNo, query)).thenReturn(single)
    }

    @Test
    fun getSearchedMoviesCompletesTest() {
        val pageNo = DataFactory.getRandomInt()
        val query = DataFactory.getRandomString()
        val topRatedMovieResponse = DataFactory.getRandomTopMovieList()
        stubGetSearchedMovies(Single.just(topRatedMovieResponse), pageNo, query)
        val testObserver = getSearchedMovies.buildUseCaseObservable(GetSearchedMovies.Params.getParams(pageNo, query)).test()
        testObserver.assertComplete()
    }

    @Test
    fun getSearchedMoviesReturnsDataTest() {
        val pageNo = DataFactory.getRandomInt()
        val query = DataFactory.getRandomString()
        val topRatedMovieResponse = DataFactory.getRandomTopMovieList()
        stubGetSearchedMovies(Single.just(topRatedMovieResponse), pageNo, query)
        val testObserver = getSearchedMovies.buildUseCaseObservable(GetSearchedMovies.Params.getParams(pageNo, query)).test()
        testObserver.assertValue(topRatedMovieResponse)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getSearchedMoviesThrowsErrorIfPageNoIsZeroTest() {
        val pageNo = 0
        val query = DataFactory.getRandomString()
        val topRatedMovieResponse = DataFactory.getRandomTopMovieList()
        stubGetSearchedMovies(Single.just(topRatedMovieResponse), pageNo, query)
        getSearchedMovies.buildUseCaseObservable(GetSearchedMovies.Params.getParams(pageNo, query)).test()
    }

    @Test(expected = IllegalArgumentException::class)
    fun getSearchedMoviesThrowsErrorIfParamsIsNullTest() {
        val pageNo = 0
        val query = DataFactory.getRandomString()
        val topRatedMovieResponse = DataFactory.getRandomTopMovieList()
        stubGetSearchedMovies(Single.just(topRatedMovieResponse), pageNo, query)
        getSearchedMovies.buildUseCaseObservable(null).test()
    }

    @Test(expected = IllegalArgumentException::class)
    fun getSearchedMoviesThrowsErrorIfObserverIsNullTest() {
        getSearchedMovies = GetSearchedMovies(moviesRepository, postExecutionThread)
        getSearchedMovies.execute(null)
    }
}