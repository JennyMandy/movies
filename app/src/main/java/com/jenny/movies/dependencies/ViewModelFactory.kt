package com.jenny.movies.dependencies

import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
open class ViewModelFactory : android.arch.lifecycle.ViewModelProvider.Factory {

    private val creators: Map<Class<out android.arch.lifecycle.ViewModel>, Provider<android.arch.lifecycle.ViewModel>>

    @Inject
    constructor(creators: Map<Class<out android.arch.lifecycle.ViewModel>, @JvmSuppressWildcards Provider<android.arch.lifecycle.ViewModel>>) {
        this.creators = creators
    }

    override fun <T : android.arch.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
        var creator: Provider<out android.arch.lifecycle.ViewModel>? = creators[modelClass]
        if (creator == null) {
            for ((key, value) in creators) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }

        if (creator == null) {
            throw IllegalStateException("Unkown model class: " + modelClass)
        }

        try {
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}