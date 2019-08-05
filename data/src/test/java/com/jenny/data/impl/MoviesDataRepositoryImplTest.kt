package com.jenny.data.impl

import com.jenny.data.store.MoviesCacheDataStore
import com.jenny.data.store.MoviesDataStoreFactory
import com.jenny.data.store.MoviesRemoteDataStore
import com.jenny.domain.factory.DataFactory
import com.jenny.domain.model.Movie
import com.jenny.domain.response.TopRatedMovieResponse
import io.reactivex.Completable
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
    private val cacheDataStore = Mockito.mock(MoviesCacheDataStore::class.java)

    private val moviesDataRepository = MoviesDataRepositoryImpl(dataStoreFactory)

    @Before
    fun setup() {
        stubRemoteDataStore()
        stubCacheDataStore()
    }

    private fun stubRemoteDataStore() {
        Mockito.`when`(dataStoreFactory.moviesRemoteDataStore).thenReturn(remoteDataStore)
    }

    private fun stubCacheDataStore() {
        Mockito.`when`(dataStoreFactory.moviesCacheDataStore).thenReturn(cacheDataStore)
    }

    private fun stubGetTopRatedMovies(single: Single<TopRatedMovieResponse>, pageNo: Int) {
        Mockito.`when`(remoteDataStore.getTopRatedMovies(pageNo)).thenReturn(single)
    }

    private fun stubGetSearchResults(single: Single<TopRatedMovieResponse>, pageNo: Int, query: String) {
        Mockito.`when`(remoteDataStore.getSearchResults(pageNo, query)).thenReturn(single)
    }

    private fun stubGetSelectedMovie(single: Single<Movie>, movieId: Int) {
        Mockito.`when`(cacheDataStore.getSelectedMovie(movieId)).thenReturn(single)
    }

    private fun stubSetSelectedMovie(completable: Completable, movie: Movie) {
        Mockito.`when`(cacheDataStore.setSelectedMovie(movie)).thenReturn(completable)
    }

    private fun stubGetFavouritedMovies(single: Single<MutableList<Movie>>) {
        Mockito.`when`(cacheDataStore.getFavouritedMovies()).thenReturn(single)
    }

    private fun stubSetFavouriteMovie(completable: Completable, movie: Movie) {
        Mockito.`when`(cacheDataStore.setFavouriteMovie(movie)).thenReturn(completable)
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

    @Test
    fun getGetSearchResultsCompletesTest() {
        val pageNo = DataFactory.getRandomInt()
        val query = DataFactory.getRandomString()
        val topRatedMovieResponse = DataFactory.getRandomTopMovieList()
        stubGetSearchResults(Single.just(topRatedMovieResponse), pageNo, query)
        val testObserver = moviesDataRepository.getSearchResults(pageNo, query).test()
        testObserver.assertComplete()
    }

    @Test
    fun getGetSearchResultsReturnsDataTest() {
        val pageNo = DataFactory.getRandomInt()
        val query = DataFactory.getRandomString()
        val topRatedMovieResponse = DataFactory.getRandomTopMovieList()
        stubGetSearchResults(Single.just(topRatedMovieResponse), pageNo, query)
        val testObserver = moviesDataRepository.getSearchResults(pageNo, query).test()
        testObserver.assertValue(topRatedMovieResponse)
    }

    @Test
    fun getSelectedMovieCompletesTest() {
        val movieId = DataFactory.getRandomInt()
        val movie = DataFactory.getRandomMovie()
        stubGetSelectedMovie(Single.just(movie), movieId)
        val testObserver = moviesDataRepository.getSelectedMovie(movieId).test()
        testObserver.assertComplete()
    }

    @Test
    fun getSelectedMovieReturnsDataTest() {
        val movieId = DataFactory.getRandomInt()
        val movie = DataFactory.getRandomMovie()
        stubGetSelectedMovie(Single.just(movie), movieId)
        val testObserver = moviesDataRepository.getSelectedMovie(movieId).test()
        testObserver.assertValue(movie)
    }

    @Test
    fun getFavouritedMoviesCompletesTest() {
        val movieList = DataFactory.getMovieList(5)
        stubGetFavouritedMovies(Single.just(movieList))
        val testObserver = moviesDataRepository.getFavouritedMovies().test()
        testObserver.assertComplete()
    }

    @Test
    fun getFavouritedMoviesReturnsDataTest() {
        val movieList = DataFactory.getMovieList(5)
        stubGetFavouritedMovies(Single.just(movieList))
        val testObserver = moviesDataRepository.getFavouritedMovies().test()
        testObserver.assertValue(movieList)
    }

    @Test
    fun setSelectedMovieCompletesTest() {
        val movie = DataFactory.getRandomMovie()
        stubSetSelectedMovie(Completable.complete(), movie)
        val testObserver = moviesDataRepository.setSelectedMovie(movie).test()
        testObserver.assertComplete()
    }

    @Test
    fun setFavouriteMovieCompletesTest() {
        val movie = DataFactory.getRandomMovie()
        stubSetFavouriteMovie(Completable.complete(), movie)
        val testObserver = moviesDataRepository.setFavouriteMovie(movie).test()
        testObserver.assertComplete()
    }
}