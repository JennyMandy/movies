package com.jenny.data.impl

import com.jenny.data.store.MoviesDataStoreFactory
import com.jenny.data.store.MoviesRemoteDataStore
import com.jenny.domain.factory.DataFactory
import com.jenny.domain.response.TopRatedMovieResponse
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class MoviesDataRepositoryImplTest {
    private val dataStoreFactory = Mockito.mock(MoviesDataStoreFactory::class.java)
    private val remoteDataStore = Mockito.mock(MoviesRemoteDataStore::class.java)

    private val moviesDataRepository = MoviesDataRepositoryImpl(dataStoreFactory)

    @Before
    fun setup() {
        stubRemoteDataStore()
    }

    private fun stubRemoteDataStore() {
        Mockito.`when`(dataStoreFactory.moviesRemoteDataStore).thenReturn(remoteDataStore)
    }

    private fun stubGetTopRatedMovies(single: Single<TopRatedMovieResponse>, pageNo: Int) {
        Mockito.`when`(remoteDataStore.getTopRatedMovies(pageNo)).thenReturn(single)
    }

    @Test
    fun getTopRatedMoviesCompletesTest() {
        val pageNo = DataFactory.getRandomInt()
        val topRatedMovieResponse = DataFactory.getRandomTopMovieList()
        stubGetTopRatedMovies(Single.just(topRatedMovieResponse), pageNo)
        val testObserver = moviesDataRepository.getTopRatedMovies(pageNo).test()
        testObserver.assertComplete()
    }

    @Test
    fun getTopRatedMoviesReturnsDataTest() {
        val pageNo = DataFactory.getRandomInt()
        val topRatedMovieResponse = DataFactory.getRandomTopMovieList()
        stubGetTopRatedMovies(Single.just(topRatedMovieResponse), pageNo)
        val testObserver = moviesDataRepository.getTopRatedMovies(pageNo).test()
        testObserver.assertValue(topRatedMovieResponse)
    }
}