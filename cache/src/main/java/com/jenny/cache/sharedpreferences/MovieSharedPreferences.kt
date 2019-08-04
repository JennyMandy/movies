package com.jenny.cache.sharedpreferences

import com.google.gson.Gson
import com.jenny.domain.model.Movie


import javax.inject.Inject

class MovieSharedPreferences @Inject
constructor(private val sharedPreferencesProvider: SharedPreferencesProvider) {
    val gson: Gson = Gson()

    val getSelectedMovie: Movie?
        get() {
            val selectedMovieDataString = sharedPreferencesProvider.sharedPreferences.getString("selectedMovie", null)
            return gson.fromJson(selectedMovieDataString, Movie::class.java)
        }

    fun saveSelectedMovie(movie: Movie?): Boolean {
        val movieJson = gson.toJson(movie)
        return sharedPreferencesProvider.editor.putString(
            "selectedMovie", movieJson
        ).commit()
    }
}