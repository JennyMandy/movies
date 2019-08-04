package com.jenny.movies

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.jenny.data.repository.MoviesRemote
import com.jenny.domain.model.Movie
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

    private var pageNo = 1;
    private var movieList: MutableList<Movie>? = null
    private lateinit var textView: TextView

    companion object {
        fun initActivity(context: Context): Intent {
            return Intent(context, TopRatedMoviesActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)
        textView = findViewById(R.id.tv)
        textView.setOnClickListener {
            getNextPage()
        }
        AndroidInjection.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GetTopRatedMoviesViewModel::class.java)
        observeGetTopRatedMovies()
        getTopRatedMovies()
    }

    private fun observeGetTopRatedMovies() {
        viewModel.observeGetTopRatedMoviesResponse().observe(this, Observer {
            when (it?.status) {
                ResourceState.SUCCESS -> {
                    val data = it.data
                    if (data != null) {
                        if (movieList == null || movieList!!.isEmpty()) {
                            movieList = data.results
                        } else {
                            movieList!!.addAll(data.results)
                        }
                    }
                }
                ResourceState.ERROR -> {

                }
                else -> {
                }
            }
        })
    }

    private fun incrementPageNo(): Int {
        return pageNo++
    }

    private fun getTopRatedMovies() {
        viewModel.getTopRatedMovies(pageNo)
    }

    private fun getNextPage() {
        incrementPageNo()
        getTopRatedMovies()
    }

}