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
import android.widget.AdapterView
import android.widget.GridView
import com.jenny.data.repository.MoviesRemote
import com.jenny.domain.model.Movie
import com.jenny.movies.Constants
import com.jenny.movies.R
import com.jenny.movies.activities.ActivityMovieDetail
import com.jenny.movies.adapters.TopMoviesAdapter
import com.jenny.movies.dependencies.ViewModelFactory
import com.jenny.presentation.state.ResourceState
import com.jenny.presentation.viewmodel.GetTopRatedMoviesViewModel
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class FragmentTopMovies : DaggerFragment() {

    @Inject
    lateinit var viewModel: GetTopRatedMoviesViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var moviesRemote: MoviesRemote

    private lateinit var gridView: GridView

    private var pageNo = 1;
    private var totalPages = 0;
    private var movieList: MutableList<Movie>? = null
    private lateinit var topMoviesAdapter: TopMoviesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_top_movies, container, false)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GetTopRatedMoviesViewModel::class.java)
        observeGetTopRatedMovies()
        getTopRatedMovies()

        gridView = view.findViewById(R.id.grid_top_movies)
        topMoviesAdapter = TopMoviesAdapter(context)
        gridView.adapter = topMoviesAdapter
        gridView.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScroll(
                view: AbsListView?,
                firstVisibleItem: Int,
                visibleItemCount: Int,
                totalItemCount: Int
            ) {
                movieList?.let {
                    if (totalPages > 0 && pageNo < totalPages && it.size > 6 && firstVisibleItem == it.size - 6) {
                        getNextPage()
                    }
                }
            }

            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {

            }
        })
        gridView.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                startActivity(Intent(context, ActivityMovieDetail::class.java))
            }

        });
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