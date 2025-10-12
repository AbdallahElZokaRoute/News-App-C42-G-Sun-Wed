package com.route.data.repository.di.news

import com.route.data.dataSources.news.remote.NewsRemoteDataSourceImpl
import com.route.data.repository.NewsRepositoryImpl
import com.route.domain.repositories.news.NewsRemoteDataSource
import com.route.domain.repositories.news.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NewsRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsNewsRepository(
        impl: NewsRepositoryImpl
    ): NewsRepository
}
