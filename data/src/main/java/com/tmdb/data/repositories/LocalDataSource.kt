package com.tmdb.data.repositories

import com.tmdb.data.models.MovieDataModel

interface LocalDataSource {
    suspend fun getMovieItems(): List<MovieDataModel>
    suspend fun getMovieItem(id: Int): MovieDataModel
    suspend fun addMovieItems(movies: List<MovieDataModel>)
    suspend fun updateMovieItem(movie: MovieDataModel)
    suspend fun clearCachedMovieItems()
}