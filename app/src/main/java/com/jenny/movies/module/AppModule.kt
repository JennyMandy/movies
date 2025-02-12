package com.jenny.movies.module

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {
    @Binds
    abstract fun bindContext(application: Application): Context
}