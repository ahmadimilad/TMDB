package com.tmdb.mappers

import com.tmdb.domain.common.Mapper
import com.tmdb.domain.entities.MovieEntity
import com.tmdb.model.MovieUiModel
import javax.inject.Inject

class MovieDomainUiMapper @Inject constructor() : Mapper<MovieEntity, MovieUiModel> {
    override fun from(i: MovieEntity): MovieUiModel {
        return MovieUiModel(
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
                    return@map MovieUiModel.GenreUiModel(
                        id = genre.id,
                        name = genre.name
                    )
                }
            }
        )
    }

    override fun to(o: MovieUiModel): MovieEntity {
        return MovieEntity(
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
                    return@map MovieEntity.GenreEntity(
                        id = genre.id,
                        name = genre.name
                    )
                }
            }
        )
    }
}