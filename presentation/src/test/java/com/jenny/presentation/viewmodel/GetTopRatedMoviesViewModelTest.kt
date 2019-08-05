package com.jenny.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jenny.domain.response.TopRatedMovieResponse
import com.jenny.domain.usecase.GetSearchedMovies
import com.jenny.domain.usecase.GetTopRatedMovies
import com.jenny.domain.usecase.SaveSelectedMovie
import com.jenny.domain.usecase.SetFavouritedMovie
import com.jenny.presentation.factory.DataFactory
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableSingleObserver
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor
import org.mockito.Mockito

@RunWith(JUnit4::class)
class GetTopRatedMoviesViewModelTest {
    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val getTopRatedMovies = mock<GetTopRatedMovies>()
    private val saveSelectedMovie = mock<SaveSelectedMovie>()
    private val setFavouritedMovie = mock<SetFavouritedMovie>()
    private val getSearchedMovies = mock<GetSearchedMovies>()

    private val getTopRatedMoviesViewModel = GetTopRatedMoviesViewModel(getTopRatedMovies, saveSelectedMovie, setFavouritedMovie, getSearchedMovies)

    @Test
    fun getTopRatedMoviesCompletesTest() {
        val pageNo = DataFactory.getRandomInt()
        getTopRatedMoviesViewModel.getTopRatedMovies(pageNo)
        verify(getTopRatedMovies).execute(
            Mockito.any<DisposableSingleObserver<TopRatedMovieResponse>>(),
            eq(GetTopRatedMovies.Params(pageNo))
        )
    }

    @Test
    fun getSearchedMoviesCompletesTest() {
        val pageNo = DataFactory.getRandomInt()
        val query = DataFactory.getRandomString()
        getTopRatedMoviesViewModel.getSearchedMovies(pageNo, query)
        verify(getSearchedMovies).execute(
            Mockito.any<DisposableSingleObserver<TopRatedMovieResponse>>(),
            eq(GetSearchedMovies.Params(pageNo, query))
        )
    }

    @Test
    fun saveSelectedMovieCompletesTest() {
        val pageNo = DataFactory.getRandomMovie()
        getTopRatedMoviesViewModel.saveSelectedMovie(pageNo)
        verify(saveSelectedMovie).execute(
            Mockito.any<DisposableCompletableObserver>(),
            eq(SaveSelectedMovie.Params(pageNo))
        )
    }

    @Test
    fun setFavouritedMovieCompletesTest() {
        val pageNo = DataFactory.getRandomMovie()
        getTopRatedMoviesViewModel.setFavouritedMovie(pageNo)
        verify(setFavouritedMovie).execute(
            Mockito.any<DisposableCompletableObserver>(),
            eq(SetFavouritedMovie.Params(pageNo))
        )
    }
}