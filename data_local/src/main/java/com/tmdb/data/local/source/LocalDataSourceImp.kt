package com.tmdb.data.local.source

import com.tmdb.data.local.database.MovieDAO
import com.tmdb.data.local.models.relations.MovieDetailsLocalModel
import com.tmdb.data.models.MovieDataModel
import com.tmdb.data.repositories.LocalDataSource
import com.tmdb.domain.common.Mapper
import javax.inject.Inject

class LocalDataSourceImp @Inject constructor(
    private val movieDAO: MovieDAO,
    private val movieMapper: Mapper<MovieDetailsLocalModel, MovieDataModel>
) : LocalDataSource {
    override suspend fun getMovieItems(): List<MovieDataModel> {
        val movieLocalList = movieDAO.getMovieItems()
        return movieMapper.fromList(movieLocalList)
    }

    override suspend fun getMovieItem(id: Int): MovieDataModel {
        val movieLocalModel = movieDAO.getMovieItem(id = id)
        return movieMapper.from(movieLocalModel)
    }

    override suspend fun addMovieItems(movies: List<MovieDataModel>) {
        val movieLocalList = movieMapper.toList(movies)
        movieDAO.insertMovieListWithGenres(movieLocalList)
    }

    override suspend fun updateMovieItem(movie: MovieDataModel) {
        val movieLocalModel = movieMapper.to(movie)
        movieDAO.updateMovieDetails(movieLocalModel)
    }

    override suspend fun clearCachedMovieItems() {
        return movieDAO.clearCachedMovieItems()
    }
}