package com.tmdb.data.local.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index

@Entity(
    tableName = "genre",
    indices = [Index(value = ["movieId", "id"], unique = true)],
    primaryKeys = ["movieId", "id"],
    foreignKeys = [ForeignKey(
        entity = MovieLocalModel::class,
        parentColumns = ["id"],
        childColumns = ["movieId"],
        onUpdate = CASCADE,
        onDelete = CASCADE
    )]
)
data class GenreLocalModel(
    val movieId: Int,
    val id: Int,
    val name: String
)