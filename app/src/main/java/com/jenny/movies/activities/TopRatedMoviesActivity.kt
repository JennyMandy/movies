package com.jenny.movies.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.View
import com.jenny.movies.R
import com.jenny.movies.adapters.TabAdapter
import com.jenny.movies.fragments.FragmentFavourites
import com.jenny.movies.fragments.FragmentTopMovies
import dagger.android.support.DaggerAppCompatActivity


class TopRatedMoviesActivity : DaggerAppCompatActivity() {
    private lateinit var tabAdapter: TabAdapter
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager

    companion object {
        fun initActivity(context: Context): Intent {
            return Intent(context, TopRatedMoviesActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)
        viewPager = findViewById<View>(R.id.viewPager) as ViewPager
        tabLayout = findViewById<View>(R.id.tabLayout) as TabLayout
        tabAdapter = TabAdapter(supportFragmentManager)
        tabAdapter.addFragment(FragmentTopMovies(), resources.getString(R.string.tab_one))
        tabAdapter.addFragment(FragmentFavourites(), resources.getString(R.string.tab_two))
        viewPager.adapter = tabAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

}