package com.jenny.movies.module

import com.jenny.domain.executor.PostExecutionThread
import com.jenny.movies.UIThread
import com.jenny.movies.activities.ActivityMovieDetail
import com.jenny.movies.activities.TopRatedMoviesActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UIModule {
    @Binds
    abstract fun bindsPostExecutionThread(uiThread: UIThread): PostExecutionThread

    @ContributesAndroidInjector(modules = [MoviesFragmentModule::class])
    abstract fun contributesTopRatedMoviesActivity(): TopRatedMoviesActivity

    @ContributesAndroidInjector
    abstract fun contributesActivityMovieDetail(): ActivityMovieDetail
}