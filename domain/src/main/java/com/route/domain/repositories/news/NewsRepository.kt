package com.route.domain.repositories.news

import com.route.domain.entities.news.ArticlesItemEntity
import com.route.domain.entities.news.SourcesItemEntity
import com.route.domain.utils.base.Resource

interface NewsRepository {
    suspend fun getSources(categoryId: String): Resource<List<SourcesItemEntity>>
    suspend fun getNewsBySource(sourceId: String): Resource<List<ArticlesItemEntity>>
}

interface NewsLocalDataSource {
    suspend fun getSources(category: String): Resource<List<SourcesItemEntity>>
    suspend fun saveSources(sources: List<SourcesItemEntity>): Resource<Unit>
}

interface NewsRemoteDataSource {
    suspend fun fetchSources(categoryId: String): Resource<List<SourcesItemEntity>>
    suspend fun fetchNewsBySource(sourceId: String): Resource<List<ArticlesItemEntity>>
}

