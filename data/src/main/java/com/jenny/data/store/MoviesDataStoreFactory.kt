package com.jenny.data.store

import javax.inject.Inject

class MoviesDataStoreFactory @Inject constructor(val moviesRemoteDataStore: MoviesRemoteDataStore) {
}