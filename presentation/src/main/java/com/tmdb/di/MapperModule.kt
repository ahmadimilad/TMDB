package com.tmdb.di

import com.tmdb.data.local.mapper.MovieLocalDataMapper
import com.tmdb.data.local.models.relations.MovieDetailsLocalModel
import com.tmdb.data.mappers.MovieDataDomainMapper
import com.tmdb.data.models.MovieDataModel
import com.tmdb.data.remote.mapper.MovieNetworkDataMapper
import com.tmdb.data.remote.model.MovieNetworkModel
import com.tmdb.domain.common.Mapper
import com.tmdb.domain.entities.MovieEntity
import com.tmdb.mappers.MovieDomainUiMapper
import com.tmdb.model.MovieUiModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MapperModule {
    //region Remote Mappers
    @Binds
    abstract fun bindsMovieNetworkDataMapper(mapper: MovieNetworkDataMapper): Mapper<MovieNetworkModel, MovieDataModel>

    //endregion

    //region Locale Mappers
    @Binds
    abstract fun bindsMovieLocalDataMapper(mapper: MovieLocalDataMapper): Mapper<MovieDetailsLocalModel, MovieDataModel>
    //endregion

    //region Data Mappers
    @Binds
    abstract fun bindsMovieDataDomainMapper(mapper: MovieDataDomainMapper): Mapper<MovieDataModel, MovieEntity>
    //endregion

    //region Presentation Mappers
    @Binds
    abstract fun bindsMovieDomainUiMapper(mapper : MovieDomainUiMapper) : Mapper<MovieEntity, MovieUiModel>

    //endregion
}