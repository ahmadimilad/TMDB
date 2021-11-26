package com.tmdb.data.local.models.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.tmdb.data.local.models.GenreLocalModel
import com.tmdb.data.local.models.MovieLocalModel

data class MovieDetailsLocalModel(
    @Embedded val movie: MovieLocalModel,
    @Relation(
        parentColumn = "id",
        entityColumn = "movieId"
    )
    var genres: List<GenreLocalModel>? = null
)