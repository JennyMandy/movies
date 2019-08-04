package com.jenny.cache.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.jenny.cache.model.CachedMovie
import io.reactivex.Single

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movies: CachedMovie)

    @Query("select * from movie")
    fun getAllMovies(): Single<MutableList<CachedMovie>>
}