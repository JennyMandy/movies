package com.jenny.presentation.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.jenny.domain.model.Movie
import com.jenny.domain.usecase.GetSelectedMovies
import com.jenny.presentation.state.Resource
import com.jenny.presentation.state.ResourceState
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class MovieDetailViewModel @Inject
constructor(private val getSelectedMovie: GetSelectedMovies) : ViewModel() {
    private val getSelectedMovieLiveData = MutableLiveData<Resource<Movie>>()

    override fun onCleared() {
        getSelectedMovie.disposeAll()
    }

    // Observe Get Selected Movie Response...
    fun observeGetSelectedMovieResponse(): LiveData<Resource<Movie>> {
        return getSelectedMovieLiveData
    }

    // Get Selected Movie Response...
    fun getSelectedMovie(movieId: Int) {
        getSelectedMovieLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        getSelectedMovie.execute(GetSelectedMovieSubscriber(), GetSelectedMovies.Params.getParams(movieId))
    }

    // Get Top Rated Movies Response Subscriber...
    private inner class GetSelectedMovieSubscriber : DisposableSingleObserver<Movie>() {
        override fun onSuccess(t: Movie) {
            getSelectedMovieLiveData.postValue(Resource(ResourceState.SUCCESS, t, null))
        }

        override fun onError(e: Throwable) {
            getSelectedMovieLiveData.postValue(Resource(ResourceState.ERROR, null, e.message))
        }

    }
}