package com.tmdb.data.remote.mapper

import com.tmdb.data.models.MovieDataModel
import com.tmdb.data.remote.model.MovieNetworkModel
import com.tmdb.domain.common.Mapper
import javax.inject.Inject

class MovieNetworkDataMapper @Inject constructor() : Mapper<MovieNetworkModel, MovieDataModel> {
    override fun from(i: MovieNetworkModel): MovieDataModel {
        return MovieDataModel(
            id = i.id,
            title = i.title,
            overview = i.overview,
            voteAverage = i.voteAverage,
            voteCount = i.voteCount,
            posterPath = i.posterPath,
            backdropPath = i.backdropPath,
            releaseDate = i.releaseDate,
            originalLanguage = i.originalLanguage,
            homepage = i.homepage,
            imdbId = i.imdbId,
            genres = i.genres?.let {
                it.map { genre ->
                    return@map MovieDataModel.GenreDataModel(
                        id = genre.id,
                        name = genre.name
                    )
                }
            }
        )
    }

    override fun to(o: MovieDataModel): MovieNetworkModel {
        return MovieNetworkModel(
            id = o.id,
            title = o.title,
            overview = o.overview,
            voteAverage = o.voteAverage,
            voteCount = o.voteCount,
            posterPath = o.posterPath,
            backdropPath = o.backdropPath,
            releaseDate = o.releaseDate,
            originalLanguage = o.originalLanguage,
            homepage = o.homepage,
            imdbId = o.imdbId,
            genres = o.genres?.let {
                it.map { genre ->
                    return@map MovieNetworkModel.GenreNetworkModel(
                        id = genre.id,
                        name = genre.name
                    )
                }
            }
        )
    }
}