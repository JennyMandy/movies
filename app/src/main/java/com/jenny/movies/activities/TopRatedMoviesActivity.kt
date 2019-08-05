package com.jenny.movies.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.ViewConfiguration
import android.view.inputmethod.InputMethodManager
import com.jenny.movies.R
import com.jenny.movies.adapters.TabAdapter
import com.jenny.movies.fragments.FragmentFavourites
import com.jenny.movies.fragments.FragmentTopMovies
import com.jenny.movies.listener.SearchListener
import dagger.android.support.DaggerAppCompatActivity


class TopRatedMoviesActivity : DaggerAppCompatActivity() {
    private lateinit var tabAdapter: TabAdapter
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private var listener: SearchListener? = null
    private var searchView: SearchView? = null

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
        makeActionOverflowMenuShown()
    }

    fun setSearchListener(listener: SearchListener) {
        this.listener = listener
    }

    private fun makeActionOverflowMenuShown() {
        try {
            val config = ViewConfiguration.get(this)
            val menuKeyField = ViewConfiguration::class.java.getDeclaredField("sHasPermanentMenuKey")
            if (menuKeyField != null) {
                menuKeyField.isAccessible = true
                menuKeyField.setBoolean(config, false)
            }
        } catch (e: Exception) {
            Log.d("", e.localizedMessage)
        }

    }

    private fun searchMovies(query: String?) {
        listener?.movieSearched(query)
        viewPager.currentItem = 0
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.search_menu).actionView as SearchView
        searchView?.let { searchView ->
            searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
            searchView.isSubmitButtonEnabled
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(string: String): Boolean {
                    searchMovies(string)
                    return true
                }

                override fun onQueryTextChange(query: String): Boolean {
                    if (query.isEmpty()) {
                        searchMovies("")
                    }
                    return true
                }
            })
        }

        return true
    }

    override fun onBackPressed() {
        if (searchView == null) {
            super.onBackPressed()
        } else {
            searchView?.let {
                if (it.isIconified) {
                    super.onBackPressed()
                } else {
                    it.onActionViewCollapsed()
                }
            }
        }
    }

}