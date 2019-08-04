package com.jenny.movies.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.jenny.movies.R
import dagger.android.support.DaggerAppCompatActivity

class ActivityMovieDetail : DaggerAppCompatActivity() {
    companion object {
        fun initActivity(context: Context): Intent {
            return Intent(context, ActivityMovieDetail::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        val poster = findViewById<ImageView>(R.id.poster)
        val name = findViewById<TextView>(R.id.name)
    }
}