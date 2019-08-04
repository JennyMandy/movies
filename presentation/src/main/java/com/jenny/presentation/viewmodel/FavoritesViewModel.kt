package com.jenny.presentation.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.jenny.domain.model.Movie
import com.jenny.domain.usecase.GetFavouritedMovies
import com.jenny.domain.usecase.SaveSelectedMovie
import com.jenny.presentation.state.Resource
import com.jenny.presentation.state.ResourceState
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class FavoritesViewModel @Inject
constructor(private val getFavouritedMovies: GetFavouritedMovies, private val saveSelectedMovie: SaveSelectedMovie) :
    ViewModel() {
    private val getFavouritedMoviesLiveData = MutableLiveData<Resource<MutableList<Movie>>>()
    private val saveSelectedMovieLiveData = MutableLiveData<Resource<Void>>()

    override fun onCleared() {
        getFavouritedMovies.disposeAll()
        saveSelectedMovie.disposeAll()
    }

    // Observe Get Favorited Movie Response...
    fun observeGetFavoritedMovieResponse(): LiveData<Resource<MutableList<Movie>>> {
        return getFavouritedMoviesLiveData
    }

    // Get Favorited Movie Response...
    fun getFavoritedMovie() {
        getFavouritedMoviesLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        getFavouritedMovies.execute(observer = GetFavouritedMoviesSubscriber())
    }

    // Observe Save Selected Movie Response...
    fun observeSaveSelectedMovieResponse(): LiveData<Resource<Void>> {
        return saveSelectedMovieLiveData
    }

    // Save Selected Movies Response...
    fun saveSelectedMovie(movie: Movie) {
        saveSelectedMovieLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        saveSelectedMovie.execute(SaveSelectedMovieSubscriber(), SaveSelectedMovie.Params.getParams(movie))
    }

    // Get Favorited Movies Response Subscriber...
    private inner class GetFavouritedMoviesSubscriber : DisposableSingleObserver<MutableList<Movie>>() {
        override fun onSuccess(t: MutableList<Movie>) {
            getFavouritedMoviesLiveData.postValue(Resource(ResourceState.SUCCESS, t, null))
        }

        override fun onError(e: Throwable) {
            getFavouritedMoviesLiveData.postValue(Resource(ResourceState.ERROR, null, e.message))
        }

    }

    // Get Selected Movies Response Subscriber...
    private inner class SaveSelectedMovieSubscriber : DisposableCompletableObserver() {
        override fun onComplete() {
            saveSelectedMovieLiveData.postValue(Resource(ResourceState.SUCCESS, null, null))
        }

        override fun onError(e: Throwable) {
            saveSelectedMovieLiveData.postValue(Resource(ResourceState.ERROR, null, e.message))
        }
    }
}