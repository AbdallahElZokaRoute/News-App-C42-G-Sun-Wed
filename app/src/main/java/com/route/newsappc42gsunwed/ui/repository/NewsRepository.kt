package com.route.newsappc42gsunwed.ui.repository

import android.content.Context
import com.route.newsappc42gsunwed.api.model.SourcesItemDM
import com.route.newsappc42gsunwed.ui.repository.dataSource.local.NewsLocalDataSource
import com.route.newsappc42gsunwed.ui.repository.dataSource.remote.NewsRemoteDataSource
import com.route.newsappc42gsunwed.ui.screens.news.Resource
import com.route.newsappc42gsunwed.utils.NetworkUtils.isInternetConnected

class NewsRepository {
    val newsLocalDataSource = NewsLocalDataSource()
    val newsRemoteDataSource = NewsRemoteDataSource()

    suspend fun getSources(category: String, context: Context): Resource<List<SourcesItemDM>> {
        val isConnected = isInternetConnected(context) // Todo Detect Internet State
        return if (isConnected) {
            val sources = newsRemoteDataSource.fetchSources(category = category)
            if (sources is Resource.Success)
                newsLocalDataSource.saveSources(sources.data)
            sources
        } else {
            newsLocalDataSource.getSavedSources(category)
        }
    }
}
// 1- SOLID Design Principles
// 2- Clean Arch (Onion Architecture) + Dependency Injection
