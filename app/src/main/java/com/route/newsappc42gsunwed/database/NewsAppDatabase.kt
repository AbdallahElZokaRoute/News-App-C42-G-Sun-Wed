package com.route.newsappc42gsunwed.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.route.newsappc42gsunwed.api.model.SourcesItemDM
import com.route.newsappc42gsunwed.database.dao.SourcesDao

@Database(entities = [SourcesItemDM::class], version = 1)
abstract class NewsAppDatabase : RoomDatabase() {
    abstract fun getSourcesDao(): SourcesDao

    companion object {
        private var INSTANCE: NewsAppDatabase? = null
        private val databaseName = "News Database"
        fun init(context: Context) {
            if (INSTANCE == null)
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    NewsAppDatabase::class.java,
                    databaseName
                )
                    .fallbackToDestructiveMigration(true)
                    .build()
        }

        fun getInstance(): NewsAppDatabase {
            return INSTANCE!!
        }
    }

}