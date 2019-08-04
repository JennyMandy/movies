package com.jenny.remote.impl

import com.jenny.domain.response.TopRatedMovieResponse
import com.jenny.remote.constants.Constants
import com.jenny.remote.factory.DataFactory
import com.jenny.remote.service.MovieListService
import com.jenny.remote.service.MoviesApiServiceFactory
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.verify

@RunWith(JUnit4::class)
class MoviesListImplTest {
    private val serviceFactory = Mockito.mock(MoviesApiServiceFactory::class.java)
    private val movieListService = Mockito.mock(MovieListService::class.java)
    private val moviesListRemoteImpl = MoviesListImpl(movieListService)

    @Before
    fun setup() {
        stubGetService()
    }

    private fun stubGetService() {
        Mockito.`when`(serviceFactory.getMoviesApiService()).thenReturn(movieListService)
    }

    private fun stubGetTopRatedMovies(single: Single<TopRatedMovieResponse>, pageNo: Int) {
        Mockito.`when`(movieListService.getTopRatedMovies(Constants.API_KEY, pageNo)).thenReturn(single)
    }

    @Test
    fun getMovieListCompletesTest() {
        val pageNo = 1
        val movieList = DataFactory.getRandomTopMovieList()
        stubGetTopRatedMovies(Single.just(movieList), pageNo)
        val testObserver = moviesListRemoteImpl.getTopRatedMovies(pageNo).test()
        testObserver.assertComplete()
    }

    @Test
    fun getMoviesListApiCalled() {
        val pageNo = 1
        val movieList = DataFactory.getRandomTopMovieList()
        stubGetTopRatedMovies(Single.just(movieList), pageNo)
        moviesListRemoteImpl.getTopRatedMovies(pageNo).test()
        verify(movieListService).getTopRatedMovies(Constants.API_KEY, pageNo)
    }

    @Test
    fun getMoviesListReturnsData() {
        val pageNo = 1
        val movieList = DataFactory.getRandomTopMovieList()
        stubGetTopRatedMovies(Single.just(movieList), pageNo)
        val testObserver = moviesListRemoteImpl.getTopRatedMovies(pageNo).test()
        testObserver.assertValue(movieList)
    }
}