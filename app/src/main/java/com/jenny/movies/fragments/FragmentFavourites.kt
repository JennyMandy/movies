package com.jenny.movies.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jenny.movies.R
import com.jenny.movies.dependencies.ViewModelFactory
import com.jenny.presentation.viewmodel.GetTopRatedMoviesViewModel
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class FragmentFavourites : DaggerFragment() {
    @Inject
    lateinit var viewModel: GetTopRatedMoviesViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favourites, container, false)
        return view
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}