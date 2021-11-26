package com.tmdb.domain.usecases

import com.tmdb.domain.common.Resource
import com.tmdb.domain.entities.MovieEntity
import com.tmdb.domain.qualifiers.IoDispatcher
import com.tmdb.domain.repositories.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val repository: Repository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(id: Int): Flow<Resource<MovieEntity>> {
        return try {
            repository.getMovieDetails(id).flowOn(dispatcher)
        } catch (exception: Exception) {
            flow { emit(Resource.Error(exception)) }
        }
    }
}