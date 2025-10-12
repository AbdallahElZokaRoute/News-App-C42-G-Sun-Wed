package com.route.data.dataSources.news.local

import com.route.data.local.database.database.NewsAppDatabase
import com.route.data.mapper.news.toEntity
import com.route.data.mapper.news.toModel
import com.route.domain.entities.news.SourcesItemEntity
import com.route.domain.repositories.news.NewsLocalDataSource
import com.route.domain.utils.base.Resource
import javax.inject.Inject

class NewsLocalDataSourceImpl @Inject constructor(
    private val roomDatabase: NewsAppDatabase
) : NewsLocalDataSource {
    override suspend fun getSources(category: String): Resource<List<SourcesItemEntity>> {
        return try {
            val sources =
                roomDatabase.getSourcesDao().getSavedSources(category).map {
                    it.toEntity()
                }
            Resource.Success(sources)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Something Went Wrong!")
        }
    }

    override suspend fun saveSources(sources: List<SourcesItemEntity>): Resource<Unit> {
        return try {
            val sourcesModels = sources.map {
                it.toModel()
            }
            roomDatabase.getSourcesDao().saveSources(sourcesModels)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Something Went Wrong!")
        }
    }

}
