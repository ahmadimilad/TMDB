package com.tmdb.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieLocalModel(
    @PrimaryKey
    val id: Int,
    val title: String,
    val overview: String? = null,
    val voteAverage: Double,
    val voteCount: Int,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val releaseDate: String,
    val originalLanguage: String,
    val homepage: String? = null,
    val imdbId: String? = null
)