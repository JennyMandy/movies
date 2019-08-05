package com.jenny.data.store

import com.jenny.data.repository.MoviesCache
import com.jenny.domain.factory.DataFactory
import com.jenny.domain.model.Movie
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class MoviesCacheDataStoreTest {
    @Mock
    private lateinit var moviesCache: MoviesCache

    private lateinit var moviesCacheDataStore: MoviesCacheDataStore

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        moviesCacheDataStore = MoviesCacheDataStore(moviesCache)
    }

    private fun stubGetSelectedMovie(single: Single<Movie>, movieId: Int) {
        Mockito.`when`(moviesCache.getSelectedMovie(movieId)).thenReturn(single)
    }

    private fun stubSetSelectedMovie(completable: Completable, movie: Movie) {
        Mockito.`when`(moviesCache.setSelectedMovie(movie)).thenReturn(completable)
    }

    private fun stubGetFavouritedMovies(single: Single<MutableList<Movie>>) {
        Mockito.`when`(moviesCache.getFavouritedMovies()).thenReturn(single)
    }

    private fun stubSetFavouriteMovie(completable: Completable, movie: Movie) {
        Mockito.`when`(moviesCache.setFavouriteMovie(movie)).thenReturn(completable)
    }

    @Test
    fun getSelectedMovieCompletesTest() {
        val movieId = DataFactory.getRandomInt()
        val movie = DataFactory.getRandomMovie()
        stubGetSelectedMovie(Single.just(movie), movieId)
        val testObserver = moviesCacheDataStore.getSelectedMovie(movieId).test()
        testObserver.assertComplete()
    }

    @Test
    fun getSelectedMovieReturnsDataTest() {
        val movieId = DataFactory.getRandomInt()
        val movie = DataFactory.getRandomMovie()
        stubGetSelectedMovie(Single.just(movie), movieId)
        val testObserver = moviesCacheDataStore.getSelectedMovie(movieId).test()
        testObserver.assertValue(movie)
    }

    @Test
    fun getFavouritedMoviesCompletesTest() {
        val movieList = DataFactory.getMovieList(5)
        stubGetFavouritedMovies(Single.just(movieList))
        val testObserver = moviesCacheDataStore.getFavouritedMovies().test()
        testObserver.assertComplete()
    }

    @Test
    fun getFavouritedMoviesReturnsDataTest() {
        val movieList = DataFactory.getMovieList(5)
        stubGetFavouritedMovies(Single.just(movieList))
        val testObserver = moviesCacheDataStore.getFavouritedMovies().test()
        testObserver.assertValue(movieList)
    }

    @Test
    fun setSelectedMovieCompletesTest() {
        val movie = DataFactory.getRandomMovie()
        stubSetSelectedMovie(Completable.complete(), movie)
        val testObserver = moviesCacheDataStore.setSelectedMovie(movie).test()
        testObserver.assertComplete()
    }

    @Test
    fun setFavouriteMovieCompletesTest() {
        val movie = DataFactory.getRandomMovie()
        stubSetFavouriteMovie(Completable.complete(), movie)
        val testObserver = moviesCacheDataStore.setFavouriteMovie(movie).test()
        testObserver.assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun getTopRatedMoviesCompleteTest() {
        moviesCacheDataStore.getTopRatedMovies(1)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getGetSearchResultsCompletesTest() {
        moviesCacheDataStore.getSearchResults(1, "Seven")
    }
}