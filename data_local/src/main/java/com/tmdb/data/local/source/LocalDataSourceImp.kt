package com.tmdb.data.local.source

import com.tmdb.data.local.database.MovieDAO
import com.tmdb.data.local.models.MovieLocalModel
import com.tmdb.data.models.MovieDataModel
import com.tmdb.data.repositories.LocalDataSource
import com.tmdb.domain.common.Mapper
import javax.inject.Inject

class LocalDataSourceImp @Inject constructor(
    private val movieDAO: MovieDAO,
    private val movieMapper: Mapper<MovieLocalModel, MovieDataModel>
) : LocalDataSource {
    override suspend fun addMovieItem(movie: MovieDataModel): Long {
        val movieLocalModel = movieMapper.to(movie)
        return movieDAO.addMovieItem(movie = movieLocalModel)
    }

    override suspend fun getMovieItem(id: Int): MovieDataModel {
        val movieLocalModel = movieDAO.getMovieItem(id = id)
        return movieMapper.from(movieLocalModel)
    }

    override suspend fun addMovieItems(movies: List<MovieDataModel>): List<Long> {
        val movieLocalList = movieMapper.toList(movies)
        return movieDAO.addMovieItems(movies = movieLocalList)
    }

    override suspend fun getMovieItems(): List<MovieDataModel> {
        val movieLocalList = movieDAO.getMovieItems()
        return movieMapper.fromList(movieLocalList)
    }

    override suspend fun updateMovieItem(movie: MovieDataModel): Int {
        val movieLocalModel = movieMapper.to(movie)
        return movieDAO.updateMovieItem(movie = movieLocalModel)
    }

    override suspend fun deleteMovieItemById(id: Int): Int {
        return movieDAO.deleteMovieItemById(id = id)
    }

    override suspend fun deleteMovieItem(movie: MovieDataModel): Int {
        val movieLocalModel = movieMapper.to(movie)
        return movieDAO.deleteMovieItem(movie = movieLocalModel)
    }

    override suspend fun clearCachedMovieItems(): Int {
        return movieDAO.clearCachedMovieItems()
    }
}