package com.tmdb.utils

fun Int.summery(): String {
    return when {
        this in 1_000..999_999 -> "${this / 1_000} K"
        this > 1_000_000 -> String.format("%.1f M", this.toFloat() / 1_000_000)
        else -> this.toString()
    }
}