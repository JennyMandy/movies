package com.jenny.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jenny.domain.model.Movie
import com.jenny.domain.usecase.*
import com.jenny.presentation.factory.DataFactory
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableSingleObserver
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class FavoritesViewModelTest {
    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val getFavouritedMovies = mock<GetFavouritedMovies>()
    private val saveSelectedMovie = mock<SaveSelectedMovie>()

    private val favoritesViewModel = FavoritesViewModel(getFavouritedMovies, saveSelectedMovie)

    @Test
    fun getFavouritedMoviesCompletesTest() {
        favoritesViewModel.getFavoritedMovie()
        verify(getFavouritedMovies).execute(
            Mockito.any<DisposableSingleObserver<MutableList<Movie>>>(),
            eq(null)
        )
    }

    @Test
    fun saveSelectedMovieCompletesTest() {
        val pageNo = DataFactory.getRandomMovie()
        favoritesViewModel.saveSelectedMovie(pageNo)
        verify(saveSelectedMovie).execute(
            Mockito.any<DisposableCompletableObserver>(),
            eq(SaveSelectedMovie.Params(pageNo))
        )
    }
}