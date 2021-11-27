package com.tmdb.domain.repositories

import com.tmdb.domain.common.Resource
import com.tmdb.domain.entities.MovieEntity
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getMovies(page: Int): Flow<Resource<List<MovieEntity>>>
    suspend fun getMovieDetails(id: Int): Flow<Resource<MovieEntity>>
}