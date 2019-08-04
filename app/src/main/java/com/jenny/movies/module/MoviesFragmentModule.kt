package com.jenny.movies.module

import com.jenny.movies.fragments.FragmentFavourites
import com.jenny.movies.fragments.FragmentTopMovies
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
public abstract class MoviesFragmentModule {
    @ContributesAndroidInjector
    @FragmentScope
    abstract fun fragmentTopMovies(): FragmentTopMovies

    @ContributesAndroidInjector
    @FragmentScope
    abstract fun fragmentFavourites(): FragmentFavourites
}