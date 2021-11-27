package com.tmdb.data.remote.source

import com.tmdb.data.models.MovieDataModel
import com.tmdb.data.remote.api.ApiService
import com.tmdb.data.remote.model.MovieNetworkModel
import com.tmdb.data.repositories.RemoteDataSource
import com.tmdb.domain.common.Mapper
import javax.inject.Inject

class RemoteDataSourceImp @Inject constructor(
    private val apiService: ApiService,
    private val movieMapper: Mapper<MovieNetworkModel, MovieDataModel>
) : RemoteDataSource {
    override suspend fun getMovies(page: Int): List<MovieDataModel> {
        return movieMapper.fromList(apiService.getMovies(page).results)
    }

    override suspend fun getMovieDetails(page: Int): MovieDataModel {
        return movieMapper.from(apiService.getMovieDetails(page))
    }
}