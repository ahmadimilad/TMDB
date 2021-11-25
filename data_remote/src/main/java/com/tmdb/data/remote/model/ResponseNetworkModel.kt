package com.tmdb.data.remote.model

import com.google.gson.annotations.SerializedName

data class ResponseNetworkModel(
    @SerializedName("results")
    val results: List<MovieNetworkModel>?,
    @SerializedName("page")
    val page: Int?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)