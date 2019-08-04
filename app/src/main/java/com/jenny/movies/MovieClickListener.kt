package com.jenny.movies

interface MovieClickListener {
    fun imageClicked(position: Int)
    fun favoriteClicked(position: Int)
}