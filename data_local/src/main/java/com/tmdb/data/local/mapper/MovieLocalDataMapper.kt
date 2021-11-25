package com.tmdb.data.local.mapper

import com.tmdb.data.local.models.MovieLocalModel
import com.tmdb.data.models.MovieDataModel
import com.tmdb.domain.common.Mapper
import javax.inject.Inject

class MovieLocalDataMapper @Inject constructor() : Mapper<MovieLocalModel, MovieDataModel> {
    override fun from(i: MovieLocalModel?): MovieDataModel {
        return MovieDataModel(
            id = i?.id,
            title = i?.title,
            overview = i?.overview,
            posterPath = i?.posterPath,
            voteAverage = i?.voteAverage,
            voteCount = i?.voteCount
        )
    }

    override fun to(o: MovieDataModel?): MovieLocalModel {
        return MovieLocalModel(
            id = o?.id,
            title = o?.title,
            overview = o?.overview,
            posterPath = o?.posterPath,
            voteAverage = o?.voteAverage,
            voteCount = o?.voteCount
        )
    }
}