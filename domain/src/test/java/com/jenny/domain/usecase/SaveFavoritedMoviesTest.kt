package com.jenny.domain.usecase

import com.jenny.domain.executor.PostExecutionThread
import com.jenny.domain.factory.DataFactory
import com.jenny.domain.model.Movie
import com.jenny.domain.repository.MoviesRepository
import com.jenny.domain.response.TopRatedMovieResponse
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
class SaveFavoritedMoviesTest {
    @Mock
    private lateinit var postExecutionThread: PostExecutionThread

    @Mock
    private lateinit var moviesRepository: MoviesRepository

    private lateinit var setFavouritedMovie: SetFavouritedMovie

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        setFavouritedMovie = SetFavouritedMovie(moviesRepository, postExecutionThread)
    }

    private fun stubSetFavouritedMovie(completable: Completable, movie: Movie) {
        Mockito.`when`(moviesRepository.setFavouriteMovie(movie)).thenReturn(completable)
    }

    @Test
    fun setFavouritedMovieCompletesTest() {
        val movie = DataFactory.getRandomMovie()
        stubSetFavouritedMovie(Completable.complete(), movie)
        val testObserver = setFavouritedMovie.buildUseCaseObservable(SetFavouritedMovie.Params.getParams(movie)).test()
        testObserver.assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun setFavouritedMovieThrowsErrorIfParamsIsNullTest() {
        val movie = DataFactory.getRandomMovie()
        stubSetFavouritedMovie(Completable.complete(), movie)
        setFavouritedMovie.buildUseCaseObservable(null).test()
    }

    @Test(expected = IllegalArgumentException::class)
    fun setFavouritedMovieThrowsErrorIfObserverIsNullTest() {
        setFavouritedMovie = SetFavouritedMovie(moviesRepository, postExecutionThread)
        setFavouritedMovie.execute(null)
    }
}