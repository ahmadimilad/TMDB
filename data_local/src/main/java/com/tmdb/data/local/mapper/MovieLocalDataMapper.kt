package com.tmdb.data.local.mapper

import com.tmdb.data.local.models.GenreLocalModel
import com.tmdb.data.local.models.MovieLocalModel
import com.tmdb.data.local.models.relations.MovieDetailsLocalModel
import com.tmdb.data.models.MovieDataModel
import com.tmdb.domain.common.Mapper
import javax.inject.Inject

class MovieLocalDataMapper @Inject constructor() : Mapper<MovieDetailsLocalModel, MovieDataModel> {
    override fun from(i: MovieDetailsLocalModel): MovieDataModel {
        return MovieDataModel(
            id = i.movie.id,
            title = i.movie.title,
            overview = i.movie.overview,
            voteAverage = i.movie.voteAverage,
            voteCount = i.movie.voteCount,
            posterPath = i.movie.posterPath,
            backdropPath = i.movie.backdropPath,
            releaseDate = i.movie.releaseDate,
            originalLanguage = i.movie.originalLanguage,
            homepage = i.movie.homepage,
            imdbId = i.movie.imdbId,
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

    override fun to(o: MovieDataModel): MovieDetailsLocalModel {
        return MovieDetailsLocalModel(
            movie = MovieLocalModel(
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
                imdbId = o.imdbId
            ),
            genres = o.genres?.let {
                it.map { genre ->
                    return@map GenreLocalModel(
                        movieId = o.id,
                        id = genre.id,
                        name = genre.name
                    )
                }
            }
        )
    }
}