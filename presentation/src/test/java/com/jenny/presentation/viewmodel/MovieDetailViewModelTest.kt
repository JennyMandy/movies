package com.jenny.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jenny.domain.model.Movie
import com.jenny.domain.usecase.*
import com.jenny.presentation.factory.DataFactory
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.observers.DisposableSingleObserver
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class MovieDetailViewModelTest {
    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val getSelectedMovie = mock<GetSelectedMovies>()

    private val getSelectedMovieViewModel = MovieDetailViewModel(getSelectedMovie)

    @Test
    fun getSelectedMovieCompletesTest() {
        val movieId = DataFactory.getRandomInt()
        getSelectedMovieViewModel.getSelectedMovie(movieId)
        verify(getSelectedMovie).execute(
            Mockito.any<DisposableSingleObserver<Movie>>(),
            eq(GetSelectedMovies.Params(movieId))
        )
    }
}