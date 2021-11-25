package com.tmdb.data.repositories

import com.tmdb.data.models.MovieDataModel

interface LocalDataSource {
    suspend fun addMovieItem(movie: MovieDataModel): Long
    suspend fun getMovieItem(id: Int): MovieDataModel
    suspend fun addMovieItems(movies: List<MovieDataModel>): List<Long>
    suspend fun getMovieItems(): List<MovieDataModel>
    suspend fun updateMovieItem(movie: MovieDataModel): Int
    suspend fun deleteMovieItemById(id: Int): Int
    suspend fun deleteMovieItem(movie: MovieDataModel): Int
    suspend fun clearCachedMovieItems(): Int
}