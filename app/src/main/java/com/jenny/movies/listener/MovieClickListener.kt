package com.jenny.movies.listener

interface MovieClickListener {
    fun imageClicked(position: Int)
    fun favoriteClicked(position: Int)
}