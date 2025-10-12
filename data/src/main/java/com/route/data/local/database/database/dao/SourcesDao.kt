package com.route.data.local.database.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.route.data.models.news.SourcesItemModel

@Dao
interface SourcesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSources(sources: List<SourcesItemModel>)

    @Query("SELECT * FROM sources WHERE category = :category")
    suspend fun getSavedSources(category: String): List<SourcesItemModel>
}
