package com.tmdb.data.local.base

import androidx.room.Insert
import androidx.room.OnConflictStrategy

interface BaseDao<T> {
    @Insert
    fun insert(obj: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(vararg obj: T)
}
