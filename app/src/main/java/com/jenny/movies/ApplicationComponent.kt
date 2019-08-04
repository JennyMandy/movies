package com.jenny.movies

import android.app.Application
import com.jenny.movies.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        UIModule::class,
        PresentationModule::class,
        DataModule::class,
        RemoteModule::class,
        CacheModule::class
    ]
)
interface ApplicationComponent {
    fun inject(app: MoviesApplication)
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}