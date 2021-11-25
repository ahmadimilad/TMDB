package com.tmdb.domain.entities

data class MovieEntity(
    val id: Int?,
    val title: String?,
    val overview: String?,
    val posterPath: String?,
    val voteAverage: Double?,
    val voteCount: Int?
)