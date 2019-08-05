package com.jenny.movies.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.jenny.domain.model.Movie
import com.jenny.movies.Constants
import com.jenny.movies.R
import com.squareup.picasso.Picasso

class FavoriteMoviesAdapter(
    private val context: Context?
) : BaseAdapter() {
    private var movieList: MutableList<Movie> = arrayListOf()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View?
        val viewHolder: ViewHolder
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_favorite_movie, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        val movie = movieList.get(position)
        viewHolder.name.text = movie.title
        Picasso.get().load(Constants.IMAGE_URL + movie.poster_path).placeholder(R.drawable.ic_movie_placeholder).into(viewHolder.poster)
        return view
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
        val name: TextView

        init {
            this.poster = view.findViewById(R.id.poster) as ImageView
            this.name = view.findViewById(R.id.name) as TextView
        }
    }
}