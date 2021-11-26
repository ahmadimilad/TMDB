package com.tmdb.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieUiModel(
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
    val imdbId: String? = null,
    val genres: List<GenreUiModel>? = null
) : Parcelable {
    @Parcelize
    data class GenreUiModel(
        val id: Int,
        val name: String
    ) : Parcelable
}