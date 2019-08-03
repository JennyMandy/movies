package com.jenny.presentation.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.jenny.domain.response.TopRatedMovieResponse
import com.jenny.domain.usecase.GetTopRatedMovies
import com.jenny.presentation.state.Resource
import com.jenny.presentation.state.ResourceState
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class GetTopRatedMoviesViewModel @Inject
constructor(private val getTopRatedMovies: GetTopRatedMovies) : ViewModel() {
    private val getTopRatedMoviesLiveData = MutableLiveData<Resource<TopRatedMovieResponse>>()

    override fun onCleared() {
        getTopRatedMovies.disposeAll()
    }

    // Observe Get Top Rated MoviesResponse...
    fun observeGetTopRatedMoviesResponse(): LiveData<Resource<TopRatedMovieResponse>> {
        return getTopRatedMoviesLiveData
    }

    // Get Top Rated Movies Response...
    fun getTopRatedMovies(pageNo: Int) {
        getTopRatedMoviesLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        getTopRatedMovies.execute(GetTopRatedMoviesSubscriber(), GetTopRatedMovies.Params.getParams(pageNo))
    }

    // Get Top Rated Movies Response Subscriber...
    private inner class GetTopRatedMoviesSubscriber : DisposableSingleObserver<TopRatedMovieResponse>() {
        override fun onSuccess(t: TopRatedMovieResponse) {
            getTopRatedMoviesLiveData.postValue(Resource(ResourceState.SUCCESS, t, null))
        }

        override fun onError(e: Throwable) {
            getTopRatedMoviesLiveData.postValue(Resource(ResourceState.ERROR, null, e.message))
        }

    }

}