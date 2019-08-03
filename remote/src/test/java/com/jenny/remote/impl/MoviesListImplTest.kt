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

@RunWith(JUnit4::class)
class MoviesListImplTest {
    private val serviceFactory = Mockito.mock(MoviesApiServiceFactory::class.java)
    private val movieListService = Mockito.mock(MovieListService::class.java)
    private val moviesListRemoteImpl = MoviesListImpl(movieListService)

    @Before
    fun setup() {
        stubGetRatingsService()
    }

    private fun stubGetRatingsService() {
        Mockito.`when`(serviceFactory.getMoviesApiService()).thenReturn(movieListService)
    }

    private fun stubGetTopRatedMovies(single: Single<TopRatedMovieResponse>, pageNo: Int) {
        Mockito.`when`(movieListService.getTopRatedMovies(Constants.API_KEY, pageNo)).thenReturn(single)
    }

    @Test
    fun getExchangeableItemsCompletesTest() {
        val pageNo = 1
        val exchangeableItemResponse = DataFactory.getRandomTopMovieList()
        stubGetTopRatedMovies(Single.just(exchangeableItemResponse), pageNo)
        val testObserver = moviesListRemoteImpl.getTopRatedMovies(pageNo).test()
        testObserver.assertComplete()
    }
}