package com.tmdb.di

import android.content.Context
import androidx.room.Room
import com.tmdb.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(context, AppDatabase::class.java, "database").build()

    @Provides
    @Singleton
    fun provideMovieDAO(appDatabase: AppDatabase) = appDatabase.movieDao()
}