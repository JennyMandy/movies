package com.jenny.data.store

import com.jenny.data.repository.MoviesDataStore
import javax.inject.Inject

class MoviesDataStoreFactory @Inject constructor(private val moviesRemoteDataStore: MoviesRemoteDataStore) {
    open fun getDataStore(isCached: Boolean): MoviesDataStore = moviesRemoteDataStore

    open fun getRemoteDataStore(): MoviesRemoteDataStore = moviesRemoteDataStore
}