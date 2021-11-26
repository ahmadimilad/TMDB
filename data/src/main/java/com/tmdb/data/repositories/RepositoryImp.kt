package com.tmdb.data.repositories

import com.tmdb.data.models.MovieDataModel
import com.tmdb.domain.common.Mapper
import com.tmdb.domain.common.Resource
import com.tmdb.domain.entities.MovieEntity
import com.tmdb.domain.repositories.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val movieMapper: Mapper<MovieDataModel, MovieEntity>
) : Repository {
    override suspend fun getMovies(page: Int): Flow<Resource<List<MovieEntity>>> {
        return flow {
            val cache = localDataSource.getMovieItems()
            if (cache.isNotEmpty()) {
                emit(Resource.Success(movieMapper.fromList(cache)))
            }

            try {
                val data = remoteDataSource.getMovies(page)
                localDataSource.addMovieItems(data)
                emit(Resource.Success(movieMapper.fromList(data)))
            } catch (ex: Exception) {
                emit(Resource.Error(ex))
            }
        }
    }

    override suspend fun getMovieDetails(id: Int): Flow<Resource<MovieEntity>> {
        return flow {
            val cache = localDataSource.getMovieItem(id)
            emit(Resource.Success(movieMapper.from(cache)))

            try {
                val data = remoteDataSource.getMovieDetails(id)
                localDataSource.updateMovieItem(data)
                emit(Resource.Success(movieMapper.from(data)))
            } catch (ex: Exception) {
                emit(Resource.Error(ex))
            }
        }
    }
}