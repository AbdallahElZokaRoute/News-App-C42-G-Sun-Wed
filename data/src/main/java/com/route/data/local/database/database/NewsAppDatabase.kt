package com.route.data.local.database.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.route.data.local.database.database.dao.SourcesDao
import com.route.data.models.news.SourcesItemModel

@Database(entities = [SourcesItemModel::class], version = 1)
abstract class NewsAppDatabase : RoomDatabase() {
    abstract fun getSourcesDao(): SourcesDao

}