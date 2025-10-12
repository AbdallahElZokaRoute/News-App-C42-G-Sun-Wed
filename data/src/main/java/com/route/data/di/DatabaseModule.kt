package com.route.data.di

import android.content.Context
import androidx.room.Room
import com.route.data.local.database.database.NewsAppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesRoomDataBaseInstance(@ApplicationContext context: Context): NewsAppDatabase {
        val databaseName = "News Database"
        return Room.databaseBuilder(
            context.applicationContext,
            NewsAppDatabase::class.java,
            databaseName
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }
}
