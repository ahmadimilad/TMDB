package com.tmdb.data.local.database

import androidx.room.*
import com.tmdb.data.local.models.GenreLocalModel
import com.tmdb.data.local.models.MovieLocalModel
import com.tmdb.data.local.models.relations.MovieDetailsLocalModel

@Dao
interface MovieDAO {
    @Query("SELECT * FROM movies WHERE id = :id")
    fun getMovieItem(id: Int): MovieDetailsLocalModel

    @Query("SELECT * FROM movies")
    fun getMovieItems(): List<MovieDetailsLocalModel>

    @Transaction
    fun insertMovieListWithGenres(movieList: List<MovieDetailsLocalModel>): List<MovieDetailsLocalModel> {
        movieList.forEach { it ->
            addMovieItem(it.movie)

            it.genres?.let {
                insertGenres(*it.toTypedArray())
            }
        }

        return movieList
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovieItem(movie: MovieLocalModel): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenres(vararg genres: GenreLocalModel)

    @Transaction
    fun updateMovieDetails(movieWithDetails: MovieDetailsLocalModel): MovieDetailsLocalModel {
        update(
            id = movieWithDetails.movie.id,
            overview = movieWithDetails.movie.overview,
            homepage = movieWithDetails.movie.homepage,
            imdbId = movieWithDetails.movie.imdbId,
            voteAverage = movieWithDetails.movie.voteAverage,
            voteCount = movieWithDetails.movie.voteCount
        )

        movieWithDetails.genres?.let {
            insertGenres(*it.toTypedArray())
        }

        return movieWithDetails
    }

    @Transaction
    @Query("UPDATE movies SET overview = :overview , homepage = :homepage , imdbId = :imdbId ,voteAverage = :voteAverage , voteCount = :voteCount WHERE id = :id")
    fun update(id: Int, overview: String?, homepage: String?, imdbId: String?, voteAverage: Double, voteCount: Int)

    @Query("DELETE FROM movies")
    fun clearCachedMovieItems()
}