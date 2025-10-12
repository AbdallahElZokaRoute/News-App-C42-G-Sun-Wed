package com.route.data.repository

import com.route.domain.entities.news.ArticlesItemEntity
import com.route.domain.entities.news.SourcesItemEntity
import com.route.domain.repositories.news.NewsLocalDataSource
import com.route.domain.repositories.news.NewsRemoteDataSource
import com.route.domain.repositories.news.NewsRepository
import com.route.domain.utils.base.Resource
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val newsLocalDataSource: NewsLocalDataSource
) : NewsRepository {
    // Dependency Inversion
    override suspend fun getSources(categoryId: String): Resource<List<SourcesItemEntity>> {
        val isConnected = true // Todo Detect Internet State
        return if (isConnected) {
            val sources = newsRemoteDataSource.fetchSources(categoryId = categoryId)
            if (sources is Resource.Success)
                newsLocalDataSource.saveSources(sources.data)
            sources
        } else {
            newsLocalDataSource.getSources(categoryId)
        }
    }

    override suspend fun getNewsBySource(sourceId: String): Resource<List<ArticlesItemEntity>> {
        return newsRemoteDataSource.fetchNewsBySource(sourceId)
    }
}
// 1- SOLID Design Principles
// 2- Clean Arch (Onion Architecture) + Dependency Injection
