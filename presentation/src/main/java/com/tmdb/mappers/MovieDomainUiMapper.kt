package com.tmdb.mappers

import com.tmdb.domain.common.Mapper
import com.tmdb.domain.entities.MovieEntity
import com.tmdb.model.MovieUiModel
import javax.inject.Inject

class MovieDomainUiMapper @Inject constructor() : Mapper<MovieEntity, MovieUiModel> {
    override fun from(i: MovieEntity?): MovieUiModel {
        return MovieUiModel(
            id = i?.id,
            title = i?.title,
            overview = i?.overview,
            posterPath = i?.posterPath,
            voteAverage = i?.voteAverage,
            voteCount = i?.voteCount
        )
    }

    override fun to(o: MovieUiModel?): MovieEntity {
        return MovieEntity(
            id = o?.id,
            title = o?.title,
            overview = o?.overview,
            posterPath = o?.posterPath,
            voteAverage = o?.voteAverage,
            voteCount = o?.voteCount
        )
    }
}