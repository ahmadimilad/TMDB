package com.tmdb.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tmdb.data.local.models.MovieLocalModel

@Database(entities = [MovieLocalModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDAO
}