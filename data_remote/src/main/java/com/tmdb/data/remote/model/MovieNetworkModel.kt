package com.tmdb.data.remote.model

import com.google.gson.annotations.SerializedName

data class MovieNetworkModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("homepage")
    val homepage: String?,
    @SerializedName("imdb_id")
    val imdbId: String?,
    @SerializedName("genres")
    val genres: List<GenreNetworkModel>?
) {
    data class GenreNetworkModel(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String
    )
}