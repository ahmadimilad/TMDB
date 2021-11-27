package com.tmdb.utils

import com.tmdb.domain.entities.MovieEntity

class TestDataGenerator {
    companion object {
        fun generateMovies(): List<MovieEntity> {
            val item1 = MovieEntity(1, "title 1", "test review 1", 7.5, 600000, releaseDate = "2021/03/15", originalLanguage = "English")
            val item2 = MovieEntity(2, "title 2", "test review 2", 8.5, 8500000, releaseDate = "2020/06/15", originalLanguage = "English")
            val item3 = MovieEntity(3, "title 3", "test review 3", 6.0, 3000, releaseDate = "2019/01/15", originalLanguage = "Persian")
            return listOf(item1, item2, item3)
        }

        fun generateMovieDetails(): MovieEntity {
            return MovieEntity(1, "title 1", "test body 1", 7.5, 600000, releaseDate = "2021/03/15", originalLanguage = "English")
        }
    }
}