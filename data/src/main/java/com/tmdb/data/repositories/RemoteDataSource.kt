package com.tmdb.data.repositories

import com.tmdb.data.models.MovieDataModel

interface RemoteDataSource {
    suspend fun getMovies(page: Int): List<MovieDataModel>
    suspend fun getMovieDetails(page: Int): MovieDataModel
}