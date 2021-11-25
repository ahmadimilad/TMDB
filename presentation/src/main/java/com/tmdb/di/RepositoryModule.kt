package com.tmdb.di

import com.tmdb.data.local.source.LocalDataSourceImp
import com.tmdb.data.remote.source.RemoteDataSourceImp
import com.tmdb.data.repositories.LocalDataSource
import com.tmdb.data.repositories.RemoteDataSource
import com.tmdb.data.repositories.RepositoryImp
import com.tmdb.domain.repositories.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideLocalDataSource(localDataSourceImpl: LocalDataSourceImp): LocalDataSource

    @Binds
    abstract fun provideRemoteDataSource(remoteDataSourceImp: RemoteDataSourceImp): RemoteDataSource

    @Binds
    @ViewModelScoped
    abstract fun provideRepository(repository: RepositoryImp): Repository
}