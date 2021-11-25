package com.tmdb.data.remote.mapper

import com.tmdb.data.models.MovieDataModel
import com.tmdb.data.remote.model.MovieNetworkModel
import com.tmdb.domain.common.Mapper
import javax.inject.Inject

class MovieNetworkDataMapper @Inject constructor() : Mapper<MovieNetworkModel, MovieDataModel> {
    override fun from(i: MovieNetworkModel?): MovieDataModel {
        return MovieDataModel(
            id = i?.id,
            title = i?.title,
            overview = i?.overview,
            posterPath = i?.posterPath,
            voteAverage = i?.voteAverage,
            voteCount = i?.voteCount
        )
    }

    override fun to(o: MovieDataModel?): MovieNetworkModel {
        return MovieNetworkModel(
            id = o?.id,
            title = o?.title,
            overview = o?.overview,
            posterPath = o?.posterPath,
            voteAverage = o?.voteAverage,
            voteCount = o?.voteCount
        )
    }
}