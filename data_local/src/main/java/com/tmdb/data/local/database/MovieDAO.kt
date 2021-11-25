package com.tmdb.data.local.database

import androidx.room.*
import com.tmdb.data.local.models.MovieLocalModel

@Dao
interface MovieDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovieItem(movie: MovieLocalModel): Long

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getMovieItem(id: Int): MovieLocalModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovieItems(movies: List<MovieLocalModel>): List<Long>

    @Query("SELECT * FROM movies")
    fun getMovieItems(): List<MovieLocalModel>

    @Update
    fun updateMovieItem(movie: MovieLocalModel): Int

    @Query("DELETE FROM movies WHERE id = :id")
    fun deleteMovieItemById(id: Int): Int

    @Delete
    fun deleteMovieItem(movie: MovieLocalModel): Int

    @Query("DELETE FROM movies")
    fun clearCachedMovieItems(): Int
}