package com.jenny.movies.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import com.jenny.domain.model.Movie
import com.jenny.movies.Constants
import com.jenny.movies.R
import com.jenny.movies.activities.ActivityMovieDetail
import com.jenny.movies.adapters.FavoriteMoviesAdapter
import com.jenny.movies.dependencies.ViewModelFactory
import com.jenny.presentation.state.ResourceState
import com.jenny.presentation.viewmodel.FavoritesViewModel
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class FragmentFavourites : DaggerFragment() {
    @Inject
    lateinit var viewModel: FavoritesViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var gridView: GridView
    private var movieId = 0;

    private var movieList: MutableList<Movie>? = null
    private lateinit var favoriteMoviesAdapter: FavoriteMoviesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favourites, container, false)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FavoritesViewModel::class.java)
        observeGetFavoritedMovieResponse()
        observeSaveSelectedMovieResponse()

        gridView = view.findViewById(R.id.grid_favorite_movies)
        favoriteMoviesAdapter = FavoriteMoviesAdapter(context)
        gridView.adapter = favoriteMoviesAdapter

        gridView.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                movieList?.let {
                    saveMovieData(it.get(position))
                    movieId = it.get(position).id
                }
            }

        })
        return view
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            getFavoritedMovie()
        }
    }

    private fun observeGetFavoritedMovieResponse() {
        viewModel.observeGetFavoritedMovieResponse().observe(this, Observer {
            when (it?.status) {
                ResourceState.SUCCESS -> {
                    val data = it.data
                    if (data != null) {
                        movieList = data
                        movieList?.let { favoriteMoviesAdapter.setList(it) }
                        favoriteMoviesAdapter.notifyDataSetChanged()
                    }
                }
                ResourceState.ERROR -> {
                    Toast.makeText(context, getString(R.string.try_again), Toast.LENGTH_SHORT).show()
                }
                else -> {
                }
            }
        })
    }

    private fun observeSaveSelectedMovieResponse() {
        viewModel.observeSaveSelectedMovieResponse().observe(this, Observer {
            when (it?.status) {
                ResourceState.SUCCESS -> {
                    val intent = Intent(context, ActivityMovieDetail::class.java)
                    intent.putExtra(Constants.MOVIE_ID, movieId)
                    startActivity(intent)
                }
                ResourceState.ERROR -> {
                    Toast.makeText(context, getString(R.string.try_again), Toast.LENGTH_SHORT).show()
                }
                else -> {
                }
            }
        })
    }

    private fun getFavoritedMovie() {
        viewModel.getFavoritedMovie()
    }

    private fun saveMovieData(movie: Movie) {
        viewModel.saveSelectedMovie(movie)
    }
}