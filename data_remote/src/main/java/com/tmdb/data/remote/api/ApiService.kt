package com.tmdb.data.remote.api

import com.tmdb.data.remote.model.ResponseNetworkModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular?sort_by=id")
    suspend fun getMovies(@Query("page") page: Int): ResponseNetworkModel
}