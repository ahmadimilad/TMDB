package com.tmdb.utils

import com.tmdb.domain.common.Resource
import com.tmdb.domain.entities.MovieEntity
import com.tmdb.domain.repositories.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeRepositoryImp @Inject constructor() : Repository {
    override suspend fun getMovies(page: Int): Flow<Resource<List<MovieEntity>>> {
        return flow { emit(Resource.Success(TestDataGenerator.generateMovies())) }
    }

    override suspend fun getMovieDetails(id: Int): Flow<Resource<MovieEntity>> {
        return flow { emit(Resource.Success(TestDataGenerator.generateMovieDetails())) }
    }
}