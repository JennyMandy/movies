package com.jenny.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jenny.domain.response.TopRatedMovieResponse
import com.jenny.domain.usecase.GetTopRatedMovies
import com.jenny.presentation.factory.DataFactory
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
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

    @Captor
    private val getTopRatedMoviesArgumentCaptor = argumentCaptor<DisposableSingleObserver<TopRatedMovieResponse>>()

    private val getTopRatedMovies = mock<GetTopRatedMovies>()

    private val getTopRatedMoviesViewModel = GetTopRatedMoviesViewModel(getTopRatedMovies)

    @Test
    fun getTopRatedMoviesCompletesTest() {
        val pageNo = DataFactory.getRandomInt()
        getTopRatedMoviesViewModel.getTopRatedMovies(pageNo)
        verify(getTopRatedMovies).execute(
            Mockito.any<DisposableSingleObserver<TopRatedMovieResponse>>(),
            eq(GetTopRatedMovies.Params(pageNo))
        )
    }
}