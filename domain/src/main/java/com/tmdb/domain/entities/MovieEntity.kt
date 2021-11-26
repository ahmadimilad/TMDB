package com.tmdb.domain.entities

data class MovieEntity(
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
    val genres: List<GenreEntity>? = null
) {
    data class GenreEntity(
        val id: Int,
        val name: String
    )
}