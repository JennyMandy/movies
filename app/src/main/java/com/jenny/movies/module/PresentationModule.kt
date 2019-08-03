package com.jenny.movies.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.jenny.movies.dependencies.ViewModelFactory
import com.jenny.movies.dependencies.ViewModelKey
import com.jenny.presentation.viewmodel.GetTopRatedMoviesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class PresentationModule {
    @Binds
    @IntoMap
    @ViewModelKey(GetTopRatedMoviesViewModel::class)
    abstract fun bindsGetTopRatedMoviesViewModel(getTopRatedMoviesViewModel: GetTopRatedMoviesViewModel): ViewModel

    @Binds
    abstract fun bindsViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}