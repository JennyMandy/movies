package com.jenny.movies

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jenny.data.repository.MoviesRemote
import com.jenny.movies.dependencies.ViewModelFactory
import com.jenny.presentation.state.ResourceState
import com.jenny.presentation.viewmodel.GetTopRatedMoviesViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

class TopRatedMoviesActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModel: GetTopRatedMoviesViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var moviesRemote: MoviesRemote

    companion object {
        fun initActivity(context: Context): Intent {
            return Intent(context, TopRatedMoviesActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)
        AndroidInjection.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GetTopRatedMoviesViewModel::class.java)
        observeGetTopRatedMovies()
        viewModel.getTopRatedMovies(1)
    }

    private fun observeGetTopRatedMovies() {
        viewModel.observeGetTopRatedMoviesResponse().observe(this, Observer {
            when(it?.status) {
                ResourceState.SUCCESS -> {
                    val data = it.data
                    if (data != null) {

                    }
                }
                ResourceState.ERROR -> {

                }
                else -> {}
            }
        })
    }

}