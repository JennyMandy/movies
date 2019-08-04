package com.jenny.data.store

import com.jenny.data.repository.MoviesRemote
import com.jenny.domain.factory.DataFactory
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
class MoviesRemoteDataStoreTest {
    @Mock
    private lateinit var moviesRemote: MoviesRemote

    private lateinit var moviesRemoteDataStore: MoviesRemoteDataStore

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        moviesRemoteDataStore = MoviesRemoteDataStore(moviesRemote)
    }

    private fun stubGetTopRatedMoviese(single: Single<TopRatedMovieResponse>, pageNo: Int) {
        Mockito.`when`(moviesRemote.getTopRatedMovies(pageNo)).thenReturn(single)
    }

    @Test
    fun getTopRatedMoviesCompleteTest() {
        val pageNo = DataFactory.getRandomInt()
        val topRatedMovieResponse = DataFactory.getRandomTopMovieList()
        stubGetTopRatedMoviese(Single.just(topRatedMovieResponse), pageNo)
        val testObserver = moviesRemoteDataStore.getTopRatedMovies(pageNo).test()
        testObserver.assertComplete()
    }

    @Test
    fun getTopRatedMoviesReturnsDataTest() {
        val pageNo = DataFactory.getRandomInt()
        val topRatedMovieResponse = DataFactory.getRandomTopMovieList()
        stubGetTopRatedMoviese(Single.just(topRatedMovieResponse), pageNo)
        val testObserver = moviesRemoteDataStore.getTopRatedMovies(pageNo).test()
        testObserver.assertValue(topRatedMovieResponse)
    }
}