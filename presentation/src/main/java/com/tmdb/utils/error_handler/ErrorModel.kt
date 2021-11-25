package com.tmdb.utils.error_handler

import com.google.gson.annotations.SerializedName

data class ErrorModel(
    val throwable: Throwable?,
    @SerializedName("status_code")
    val statusCode: Int? = 0,
    @SerializedName("status_message")
    val statusMessage: String,
    @SerializedName("success")
    val success: Boolean? = false
)