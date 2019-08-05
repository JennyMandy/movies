package com.jenny.data.store

import com.jenny.data.repository.MoviesRemote
import com.jenny.domain.factory.DataFactory
import com.jenny.domain.model.Movie
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

    private fun stubGetTopRatedMovies(single: Single<TopRatedMovieResponse>, pageNo: Int) {
        Mockito.`when`(moviesRemote.getTopRatedMovies(pageNo)).thenReturn(single)
    }

    private fun stubGetSearchResults(single: Single<TopRatedMovieResponse>, pageNo: Int, query: String) {
        Mockito.`when`(moviesRemote.getSearchResults(pageNo, query)).thenReturn(single)
    }

    @Test
    fun getTopRatedMoviesCompleteTest() {
        val pageNo = DataFactory.getRandomInt()
        val topRatedMovieResponse = DataFactory.getRandomTopMovieList()
        stubGetTopRatedMovies(Single.just(topRatedMovieResponse), pageNo)
        val testObserver = moviesRemoteDataStore.getTopRatedMovies(pageNo).test()
        testObserver.assertComplete()
    }

    @Test
    fun getTopRatedMoviesReturnsDataTest() {
        val pageNo = DataFactory.getRandomInt()
        val topRatedMovieResponse = DataFactory.getRandomTopMovieList()
        stubGetTopRatedMovies(Single.just(topRatedMovieResponse), pageNo)
        val testObserver = moviesRemoteDataStore.getTopRatedMovies(pageNo).test()
        testObserver.assertValue(topRatedMovieResponse)
    }

    @Test
    fun getGetSearchResultsCompletesTest() {
        val pageNo = DataFactory.getRandomInt()
        val query = DataFactory.getRandomString()
        val topRatedMovieResponse = DataFactory.getRandomTopMovieList()
        stubGetSearchResults(Single.just(topRatedMovieResponse), pageNo, query)
        val testObserver = moviesRemoteDataStore.getSearchResults(pageNo, query).test()
        testObserver.assertComplete()
    }

    @Test
    fun getGetSearchResultsReturnsDataTest() {
        val pageNo = DataFactory.getRandomInt()
        val query = DataFactory.getRandomString()
        val topRatedMovieResponse = DataFactory.getRandomTopMovieList()
        stubGetSearchResults(Single.just(topRatedMovieResponse), pageNo, query)
        val testObserver = moviesRemoteDataStore.getSearchResults(pageNo, query).test()
        testObserver.assertValue(topRatedMovieResponse)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getSelectedMovieCompletesTest() {
        moviesRemoteDataStore.getSelectedMovie(1)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getFavouritedMoviesCompletesTest() {
        moviesRemoteDataStore.getFavouritedMovies()
    }

    @Test(expected = IllegalArgumentException::class)
    fun setSelectedMovieCompletesTest() {
        moviesRemoteDataStore.setSelectedMovie(Movie())
    }

    @Test(expected = IllegalArgumentException::class)
    fun setFavouriteMovieCompletesTest() {
        moviesRemoteDataStore.setFavouriteMovie(Movie())
    }

}