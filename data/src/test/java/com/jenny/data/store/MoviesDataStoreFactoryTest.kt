package com.jenny.data.store

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class MoviesDataStoreFactoryTest {
    @Mock
    private lateinit var moviesRemoteDataStore: MoviesRemoteDataStore
    @Mock
    private lateinit var moviesCacheDataStore: MoviesCacheDataStore

    private lateinit var moviesDataStoreFactory: MoviesDataStoreFactory

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        moviesDataStoreFactory = MoviesDataStoreFactory(moviesRemoteDataStore, moviesCacheDataStore)
    }

    @Test
    fun getsRemoteDataStoreCorrectlyTest() {
        Assert.assertEquals(moviesRemoteDataStore, moviesDataStoreFactory.moviesRemoteDataStore)
    }

    @Test
    fun getsCacheDataStoreCorrectlyTest() {
        Assert.assertEquals(moviesCacheDataStore, moviesDataStoreFactory.moviesCacheDataStore)
    }
}