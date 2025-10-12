package com.route.data.remote.api

import com.route.data.models.news.NewsResponseModel
import com.route.data.models.news.SourcesResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("sources")
    suspend fun getSources(
        @Query("category") categoryApiID: String? = null
    ): Response<SourcesResponseModel>

    // enqueue    /   execute
    //    Compatible with Kotlin Coroutines
    //   Pause / Resume Technique
    @GET("everything")
    suspend fun getNewsBySource(
        @Query("sources") sourceId: String? = null,
//        @Query("page") page: Int,
//        @Query("pageSize") pageSize: Int = 10
    ): Response<NewsResponseModel>   //  NewsResponse or Response<NewsResponse>
}
