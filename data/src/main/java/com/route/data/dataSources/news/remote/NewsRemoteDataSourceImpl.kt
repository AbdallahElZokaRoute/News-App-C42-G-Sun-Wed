package com.route.data.dataSources.news.remote

import com.google.gson.Gson
import com.route.data.mapper.news.toEntity
import com.route.data.models.news.SourcesResponseModel
import com.route.data.remote.api.NewsService
import com.route.domain.entities.news.ArticlesItemEntity
import com.route.domain.entities.news.SourcesItemEntity
import com.route.domain.repositories.news.NewsRemoteDataSource
import com.route.domain.utils.base.Resource
import javax.inject.Inject

class NewsRemoteDataSourceImpl @Inject constructor(
    private val service: NewsService
) : NewsRemoteDataSource {
    override suspend fun fetchSources(categoryId: String): Resource<List<SourcesItemEntity>> {
        try {
            val response = service.getSources(categoryApiID = categoryId)
            if (response.isSuccessful) {
                val sources = response.body()?.sources?.map { it.toEntity() } ?: listOf()
                return Resource.Success(sources)
            } else {
                val errorBody = response.errorBody()?.string()
                val gson = Gson()
                val sourcesResponse = gson.fromJson(errorBody, SourcesResponseModel::class.java)
                return Resource.Error(sourcesResponse.message ?: "Something went wrong")
            }
        } catch (e: Exception) {
            return Resource.Error(e.message ?: "Something went wrong")
        }
    }

    override suspend fun fetchNewsBySource(sourceId: String): Resource<List<ArticlesItemEntity>> {
        try {
            val response = service.getNewsBySource(sourceId)
            if (response.isSuccessful) {
                val articles = response.body()?.articles?.map { it.toEntity() } ?: listOf()
                return Resource.Success(articles)
            } else {
                val errorBody = response.errorBody()?.string()
                val gson = Gson()
                val sourcesResponse = gson.fromJson(errorBody, SourcesResponseModel::class.java)
                return Resource.Error(sourcesResponse.message ?: "Something went wrong")
            }
        } catch (e: Exception) {
            return Resource.Error(e.message ?: "Something went wrong")
        }
    }
}
