package com.tmdb.data.remote.api

import com.tmdb.data.remote.model.MovieListResponseModel
import com.tmdb.data.remote.model.MovieNetworkModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular?sort_by=id")
    suspend fun getMovies(@Query("page") page: Int): MovieListResponseModel

    @GET("movie/{id}")
    suspend fun getMovieDetails(@Path("id") id: Int): MovieNetworkModel
}