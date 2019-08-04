package com.jenny.movies.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.GridView
import android.widget.ProgressBar
import com.jenny.domain.model.Movie
import com.jenny.movies.Constants
import com.jenny.movies.R
import com.jenny.movies.activities.ActivityMovieDetail
import com.jenny.movies.activities.TopRatedMoviesActivity
import com.jenny.movies.adapters.TopMoviesAdapter
import com.jenny.movies.dependencies.ViewModelFactory
import com.jenny.movies.listener.MovieClickListener
import com.jenny.movies.listener.SearchListener
import com.jenny.presentation.state.ResourceState
import com.jenny.presentation.viewmodel.GetTopRatedMoviesViewModel
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class FragmentTopMovies : DaggerFragment(), MovieClickListener, SearchListener {
    @Inject
    lateinit var viewModel: GetTopRatedMoviesViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var gridView: GridView

    private var pageNo = 1
    private var totalPages = 0
    private var movieId = 0
    private var movieList: MutableList<Movie>? = null
    private lateinit var topMoviesAdapter: TopMoviesAdapter
    private var isSearchSet = false
    private var searchQuery: String? = null

    private var spinner: ProgressBar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_top_movies, container, false)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GetTopRatedMoviesViewModel::class.java)

        observeGetTopRatedMovies()
        observeSaveSelectedMovieResponse()
        observeSetFavouritedMovieResponse()
        observeGetSearchedMovies()

        getTopRatedMovies()
        gridView = view.findViewById(R.id.grid_top_movies)
        spinner = view.findViewById(R.id.progressBar)
        topMoviesAdapter = TopMoviesAdapter(context, this)
        gridView.adapter = topMoviesAdapter
        gridView.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScroll(
                view: AbsListView?,
                firstVisibleItem: Int,
                visibleItemCount: Int,
                totalItemCount: Int
            ) {
                movieList?.let {
                    if (totalPages > 0 && pageNo < totalPages && it.size > 6 && firstVisibleItem == it.size - 4) {
                        getNextPage()
                    }
                }
            }

            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {

            }
        })
        (activity as TopRatedMoviesActivity).setSearchListener(this)
        setSpinnerVisiblility(true)
        return view
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    private fun observeGetTopRatedMovies() {
        viewModel.observeGetTopRatedMoviesResponse().observe(this, Observer {
            when (it?.status) {
                ResourceState.SUCCESS -> {
                    setSpinnerVisiblility(false)
                    val data = it.data
                    if (data != null) {
                        totalPages = data.total_pages
                        if (movieList == null) {
                            movieList = data.results
                        } else {
                            movieList?.addAll(data.results)
                        }
                        movieList?.let { topMoviesAdapter.setList(it) }
                        topMoviesAdapter.notifyDataSetChanged()
                    }
                }
                ResourceState.ERROR -> {

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
                    if (movieId > 0) {
                        val intent = Intent(context, ActivityMovieDetail::class.java)
                        intent.putExtra(Constants.MOVIE_ID, movieId)
                        startActivity(intent)
                    }
                }
                ResourceState.ERROR -> {

                }
                else -> {
                }
            }
        })
    }

    private fun observeSetFavouritedMovieResponse() {
        viewModel.observeSetFavouritedMovieResponse().observe(this, Observer {
            when (it?.status) {
                ResourceState.SUCCESS -> {

                }
                ResourceState.ERROR -> {

                }
                else -> {
                }
            }
        })
    }

    private fun observeGetSearchedMovies() {
        viewModel.observeGetSearchedMoviesResponse().observe(this, Observer {
            when (it?.status) {
                ResourceState.SUCCESS -> {
                    setSpinnerVisiblility(false)
                    val data = it.data
                    if (data != null) {
                        totalPages = data.total_pages
                        if (movieList == null) {
                            movieList = data.results
                        } else {
                            movieList?.addAll(data.results)
                        }
                        movieList?.let { topMoviesAdapter.setList(it) }
                        topMoviesAdapter.notifyDataSetChanged()
                        if (data.page == 1) {
                            gridView.setSelection(0)
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

    private fun saveMovieData(movie: Movie) {
        viewModel.saveSelectedMovie(movie)
    }

    private fun setFavouritedMovie(movie: Movie) {
        viewModel.setFavouritedMovie(movie)
    }

    private fun getSearchedMovies() {
        if (searchQuery != null) {
            searchQuery?.let {
                viewModel.getSearchedMovies(pageNo, it)
            }
            setSpinnerVisiblility(true)
        } else {
            viewModel.getTopRatedMovies(pageNo)
            setSpinnerVisiblility(true)
        }
    }

    private fun getNextPage() {
        incrementPageNo()
        if (isSearchSet) {
            getSearchedMovies()
        } else {
            getTopRatedMovies()
        }
    }

    private fun resetPageData() {
        pageNo = 1
        totalPages = 0
        movieId = 0
        movieList = null
    }

    private fun setSpinnerVisiblility(visible: Boolean) {
        if (visible) {
            spinner?.visibility = View.VISIBLE
            gridView.visibility = View.GONE
        } else {
            spinner?.visibility = View.GONE
            gridView.visibility = View.VISIBLE
        }
    }

    override fun imageClicked(position: Int) {
        movieList?.let {
            saveMovieData(it.get(position))
            movieId = it.get(position).id
        }
    }

    override fun favoriteClicked(position: Int) {
        movieList?.let {
            setFavouritedMovie(it.get(position))
        }
    }

    override fun movieSearched(query: String?) {
        resetPageData()
        isSearchSet = true
        searchQuery = query
        getSearchedMovies()
    }
}