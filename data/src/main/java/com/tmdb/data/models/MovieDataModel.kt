package com.tmdb.data.models

data class MovieDataModel(
    val id: Int?,
    val title: String?,
    val overview: String?,
    val posterPath: String?,
    val voteAverage: Double?,
    val voteCount: Int?
)