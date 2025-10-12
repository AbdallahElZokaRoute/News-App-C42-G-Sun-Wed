package com.route.data.dataSources.di.news

import com.route.data.dataSources.news.local.NewsLocalDataSourceImpl
import com.route.data.dataSources.news.remote.NewsRemoteDataSourceImpl
import com.route.domain.repositories.news.NewsLocalDataSource
import com.route.domain.repositories.news.NewsRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NewsDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindsNewsRemoteDataSource(
        impl: NewsRemoteDataSourceImpl
    ): NewsRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindsNewsLocalDataSource(
        impl: NewsLocalDataSourceImpl
    ): NewsLocalDataSource

}