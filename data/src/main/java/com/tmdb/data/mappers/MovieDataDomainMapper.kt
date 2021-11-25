package com.tmdb.data.mappers

import com.tmdb.data.models.MovieDataModel
import com.tmdb.domain.common.Mapper
import com.tmdb.domain.entities.MovieEntity
import javax.inject.Inject

class MovieDataDomainMapper @Inject constructor() : Mapper<MovieDataModel, MovieEntity> {
    override fun from(i: MovieDataModel?): MovieEntity {
        return MovieEntity(
            id = i?.id,
            title = i?.title,
            overview = i?.overview,
            posterPath = i?.posterPath,
            voteAverage = i?.voteAverage,
            voteCount = i?.voteCount
        )
    }

    override fun to(o: MovieEntity?): MovieDataModel {
        return MovieDataModel(
            id = o?.id,
            title = o?.title,
            overview = o?.overview,
            posterPath = o?.posterPath,
            voteAverage = o?.voteAverage,
            voteCount = o?.voteCount
        )
    }
}