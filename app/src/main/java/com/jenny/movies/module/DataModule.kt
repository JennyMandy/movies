package com.jenny.movies.module

import com.jenny.data.impl.MoviesDataRepositoryImpl
import com.jenny.domain.repository.MoviesRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {
    @Binds
    abstract fun bindsMoviesDataRepository(moviesDataRepositoryImpl: MoviesDataRepositoryImpl): MoviesRepository
}