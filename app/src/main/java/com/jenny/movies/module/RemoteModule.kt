package com.jenny.movies.module

import com.jenny.data.repository.MoviesRemote
import com.jenny.remote.impl.MoviesListImpl
import com.jenny.remote.service.MovieListService
import com.jenny.remote.service.MoviesApiServiceFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class RemoteModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        @Singleton
        fun providesMoviesApiService(): MovieListService {
            return MoviesApiServiceFactory.getMoviesApiService()
        }
    }

    @Binds
    abstract fun bindsMoviesRemote(moviesListImpl: MoviesListImpl): MoviesRemote
}