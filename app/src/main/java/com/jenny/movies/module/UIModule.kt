package com.jenny.movies.module

import com.jenny.domain.executor.PostExecutionThread
import com.jenny.movies.TopRatedMoviesActivity
import com.jenny.movies.UIThread
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UIModule {
    @Binds
    abstract fun bindsPostExecutionThread(uiThread: UIThread): PostExecutionThread

    @ContributesAndroidInjector
    abstract fun contributesTopRatedMoviesActivity(): TopRatedMoviesActivity
}