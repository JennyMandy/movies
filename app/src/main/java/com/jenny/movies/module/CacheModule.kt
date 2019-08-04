package com.jenny.movies.module

import android.app.Application
import com.jenny.cache.db.MovieDB
import com.jenny.cache.impl.MovieCacheImpl
import com.jenny.data.repository.MoviesCache
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class CacheModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun providesDatabase(application: Application): MovieDB {
            return MovieDB.getInstance(application)
        }
    }

    @Binds
    abstract fun bindsMovieCacheImpl(movieCacheImpl: MovieCacheImpl): MoviesCache
}