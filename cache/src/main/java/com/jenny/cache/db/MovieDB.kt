package com.jenny.cache.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.jenny.cache.dao.MovieDao
import com.jenny.cache.model.CachedMovie

@Database(entities = arrayOf(CachedMovie::class), version = 1)
abstract class MovieDB : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        private var INSTANCE: MovieDB? = null
        private val lock = Any()

        fun getInstance(context: Context): MovieDB {
            if (INSTANCE == null) {
                synchronized(lock) {
                    if (INSTANCE == null) {
                        INSTANCE =
                            Room.databaseBuilder(context.applicationContext, MovieDB::class.java, "movies.db").build()
                    }
                    return INSTANCE as MovieDB
                }
            }
            return INSTANCE as MovieDB
        }
    }
}