package com.tmdb.data.models

data class MovieDataModel(
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
    var genres: List<GenreDataModel>? = null
) {
    data class GenreDataModel(
        val id: Int,
        val name: String
    )
}