package com.jenny.movies.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.jenny.domain.model.Movie
import com.jenny.movies.Constants
import com.jenny.movies.R
import com.jenny.movies.listener.MovieClickListener
import com.squareup.picasso.Picasso

class TopMoviesAdapter(
    private val context: Context?,
    private val movieClickListener: MovieClickListener
) : BaseAdapter() {
    private var movieList: MutableList<Movie> = arrayListOf()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View?
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        val movie = movieList.get(position)
        viewHolder.name.text = movie.title
        Picasso.get().load(Constants.IMAGE_URL + movie.poster_path).placeholder(R.drawable.ic_movie_placeholder)
            .into(viewHolder.poster)
        viewHolder.poster.setOnClickListener {
            movieClickListener.imageClicked(position)
        }
        if (movie.isShortlisted) {
            viewHolder.addToFavorites.setImageDrawable(context?.resources?.getDrawable(R.drawable.ic_favorite_selected))
        } else {
            viewHolder.addToFavorites.setImageDrawable(context?.resources?.getDrawable(R.drawable.ic_favorite))
        }
        viewHolder.addToFavorites.setOnClickListener {
            viewHolder.addToFavorites.setImageDrawable(context?.resources?.getDrawable(R.drawable.ic_favorite_selected))
            animate(context, viewHolder.addToFavorites, R.anim.add_to_favorites)
            movieClickListener.favoriteClicked(position)
        }
        return view
    }

    fun animate(context: Context?, viewToAnimate: View?, animation: Int) {
        if (context != null && viewToAnimate != null) {
            val fadeoutAnimation = AnimationUtils.loadAnimation(context, animation)
            viewToAnimate.startAnimation(fadeoutAnimation)
        }
    }

    override fun getItem(position: Int): Any {
        return movieList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return movieList.size
    }

    fun setList(list: MutableList<Movie>) {
        movieList = list
    }

    private class ViewHolder(view: View) {
        val poster: ImageView
        val addToFavorites: ImageView
        val name: TextView

        init {
            this.poster = view.findViewById(R.id.poster) as ImageView
            this.name = view.findViewById(R.id.name) as TextView
            this.addToFavorites = view.findViewById(R.id.addToFavorites) as ImageView
        }
    }
}