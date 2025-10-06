package com.route.newsappc42gsunwed.ui.repository.dataSource.remote

import com.google.gson.Gson
import com.route.newsappc42gsunwed.api.ApiManager
import com.route.newsappc42gsunwed.api.model.SourcesItemDM
import com.route.newsappc42gsunwed.api.model.SourcesResponse
import com.route.newsappc42gsunwed.ui.screens.news.Resource

class NewsRemoteDataSource {
    suspend fun fetchSources(category: String): Resource<List<SourcesItemDM>> {
        try {

            val response = ApiManager.getNewsService().getSources(categoryApiID = category)
            if (response.isSuccessful) {
                val sources = response.body()?.sources ?: listOf()
                return Resource.Success(sources)
            } else {
                val errorBody = response.errorBody()?.string()
                val gson = Gson()
                val sourcesResponse = gson.fromJson(errorBody, SourcesResponse::class.java)
                return Resource.Error(sourcesResponse.message ?: "Something went wrong")
            }
        } catch (e: Exception) {
            return Resource.Error(e.message ?: "Something went wrong")
        }
    }
}
