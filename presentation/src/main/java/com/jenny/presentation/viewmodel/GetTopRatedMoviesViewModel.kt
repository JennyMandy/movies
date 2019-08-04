package com.jenny.presentation.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.jenny.domain.model.Movie
import com.jenny.domain.response.TopRatedMovieResponse
import com.jenny.domain.usecase.GetTopRatedMovies
import com.jenny.domain.usecase.SaveSelectedMovie
import com.jenny.domain.usecase.SetFavouritedMovie
import com.jenny.presentation.state.Resource
import com.jenny.presentation.state.ResourceState
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class GetTopRatedMoviesViewModel @Inject
constructor(
    private val getTopRatedMovies: GetTopRatedMovies,
    private val saveSelectedMovie: SaveSelectedMovie,
    private val setFavouritedMovie: SetFavouritedMovie
) : ViewModel() {
    private val getTopRatedMoviesLiveData = MutableLiveData<Resource<TopRatedMovieResponse>>()
    private val saveSelectedMovieLiveData = MutableLiveData<Resource<Void>>()
    private val setFavouritedMovieLiveData = MutableLiveData<Resource<Void>>()

    override fun onCleared() {
        getTopRatedMovies.disposeAll()
        saveSelectedMovie.disposeAll()
        setFavouritedMovie.disposeAll()
    }

    // Observe Get Top Rated Movies Response...
    fun observeGetTopRatedMoviesResponse(): LiveData<Resource<TopRatedMovieResponse>> {
        return getTopRatedMoviesLiveData
    }

    // Get Top Rated Movies Response...
    fun getTopRatedMovies(pageNo: Int) {
        getTopRatedMoviesLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        getTopRatedMovies.execute(GetTopRatedMoviesSubscriber(), GetTopRatedMovies.Params.getParams(pageNo))
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

    // Observe Save Selected Movie Response...
    fun observeSetFavouritedMovieResponse(): LiveData<Resource<Void>> {
        return setFavouritedMovieLiveData
    }

    // Save Selected Movies Response...
    fun setFavouritedMovie(movie: Movie) {
        setFavouritedMovieLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        setFavouritedMovie.execute(SetFavouritedMovieSubscriber(), SetFavouritedMovie.Params.getParams(movie))
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

    // Get Selected Movies Response Subscriber...
    private inner class SaveSelectedMovieSubscriber : DisposableCompletableObserver() {
        override fun onComplete() {
            saveSelectedMovieLiveData.postValue(Resource(ResourceState.SUCCESS, null, null))
        }

        override fun onError(e: Throwable) {
            saveSelectedMovieLiveData.postValue(Resource(ResourceState.ERROR, null, e.message))
        }
    }

    // Set Favourited Movies Response Subscriber...
    private inner class SetFavouritedMovieSubscriber : DisposableCompletableObserver() {
        override fun onComplete() {
            setFavouritedMovieLiveData.postValue(Resource(ResourceState.SUCCESS, null, null))
        }

        override fun onError(e: Throwable) {
            setFavouritedMovieLiveData.postValue(Resource(ResourceState.ERROR, null, e.message))
        }
    }

    /*

    // Observe Get Searched Movies Response...
    fun observeGetSearchedMoviesResponse(): LiveData<Resource<TopRatedMovieResponse>> {
        return getSearchedMoviesLiveData
    }

    // Get Searched Movies Response...
    fun getSearchedMovies(pageNo: Int, query: String) {
        getSearchedMoviesLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        getSearchedMovies.execute(GetSearchedMoviesSubscriber(), GetSearchedMovies.Params.getParams(pageNo, query))
    }

    // Get Searched Movies Response Subscriber...
    private inner class GetSearchedMoviesSubscriber : DisposableSingleObserver<TopRatedMovieResponse>() {
        override fun onSuccess(t: TopRatedMovieResponse) {
            getSearchedMoviesLiveData.postValue(Resource(ResourceState.SUCCESS, t, null))
        }

        override fun onError(e: Throwable) {
            getSearchedMoviesLiveData.postValue(Resource(ResourceState.ERROR, null, e.message))
        }

    }

     */

}