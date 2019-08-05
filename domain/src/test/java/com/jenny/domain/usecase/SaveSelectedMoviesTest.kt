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
class SaveSelectedMoviesTest {
    @Mock
    private lateinit var postExecutionThread: PostExecutionThread

    @Mock
    private lateinit var moviesRepository: MoviesRepository

    private lateinit var saveSelectedMovie: SaveSelectedMovie

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        saveSelectedMovie = SaveSelectedMovie(moviesRepository, postExecutionThread)
    }

    private fun stubSaveSelectedMovie(completable: Completable, movie: Movie) {
        Mockito.`when`(moviesRepository.setSelectedMovie(movie)).thenReturn(completable)
    }

    @Test
    fun saveSelectedMovieCompletesTest() {
        val movie = DataFactory.getRandomMovie()
        stubSaveSelectedMovie(Completable.complete(), movie)
        val testObserver = saveSelectedMovie.buildUseCaseObservable(SaveSelectedMovie.Params.getParams(movie)).test()
        testObserver.assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun saveSelectedMovieThrowsErrorIfParamsIsNullTest() {
        val movie = DataFactory.getRandomMovie()
        stubSaveSelectedMovie(Completable.complete(), movie)
        saveSelectedMovie.buildUseCaseObservable(null).test()
    }

    @Test(expected = IllegalArgumentException::class)
    fun saveSelectedMovieThrowsErrorIfObserverIsNullTest() {
        saveSelectedMovie = SaveSelectedMovie(moviesRepository, postExecutionThread)
        saveSelectedMovie.execute(null)
    }
}