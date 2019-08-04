package com.jenny.movies.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.jenny.movies.Constants
import com.jenny.movies.R
import com.jenny.movies.dependencies.ViewModelFactory
import com.jenny.presentation.state.ResourceState
import com.jenny.presentation.viewmodel.MovieDetailViewModel
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class ActivityMovieDetail : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModel: MovieDetailViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var poster: ImageView
    lateinit var name: TextView

    companion object {
        fun initActivity(context: Context): Intent {
            return Intent(context, ActivityMovieDetail::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieDetailViewModel::class.java)
        poster = findViewById<ImageView>(R.id.poster)
        name = findViewById<TextView>(R.id.name)
        observeGetSelectedMovieResponse()
        viewModel.getSelectedMovie(intent.getIntExtra(Constants.MOVIE_ID, 0))
    }

    private fun observeGetSelectedMovieResponse() {
        viewModel.observeGetSelectedMovieResponse().observe(this, Observer {
            when (it?.status) {
                ResourceState.SUCCESS -> {
                    val movie = it.data
                    name.text = movie?.title
                    Picasso.get().load(Constants.IMAGE_URL + movie?.poster_path)
                        .placeholder(R.drawable.abc_ic_ab_back_material).into(poster)
                }
                ResourceState.ERROR -> {

                }
                else -> {
                }
            }
        })
    }
}