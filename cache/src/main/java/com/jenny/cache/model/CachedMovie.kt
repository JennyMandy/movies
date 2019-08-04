package com.jenny.cache.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.jenny.cache.constants.Constants.IS_SHORTLISTED
import com.jenny.cache.constants.Constants.MOVIE_ID
import com.jenny.cache.constants.Constants.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class CachedMovie(
    val poster_path: String?,
    val adult: Boolean,
    val overview: String?,
    val release_date: String?,
    @PrimaryKey
    @ColumnInfo(name = MOVIE_ID)
    val id: Int,
    val original_title: String?,
    val original_language: String?,
    val title: String?,
    val backdrop_path: String?,
    val popularity: Double,
    val vote_count: Int,
    val video: Boolean,
    val vote_average: Double,
    @ColumnInfo(name = IS_SHORTLISTED)
    val isShortlisted: Boolean
)