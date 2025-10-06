package com.route.newsappc42gsunwed.ui.repository.dataSource.local

import com.route.newsappc42gsunwed.api.model.SourcesItemDM
import com.route.newsappc42gsunwed.database.NewsAppDatabase
import com.route.newsappc42gsunwed.ui.screens.news.Resource

class NewsLocalDataSource {
    suspend fun getSavedSources(category: String): Resource<List<SourcesItemDM>> {
        return try {
            val sources = NewsAppDatabase.getInstance().getSourcesDao().getSavedSources(category)
            Resource.Success(sources)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Something Went Wrong!")
        }
    }

    suspend fun saveSources(sources: List<SourcesItemDM>): Resource<Unit> {
        return try {
            NewsAppDatabase.getInstance().getSourcesDao().saveSources(sources)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Something Went Wrong!")
        }
    }
}
