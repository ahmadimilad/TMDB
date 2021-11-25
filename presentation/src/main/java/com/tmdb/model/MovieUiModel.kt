package com.tmdb.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieUiModel(
    val id: Int?,
    val title: String?,
    val overview: String?,
    val posterPath: String?,
    val voteAverage: Double?,
    val voteCount: Int?
) : Parcelable