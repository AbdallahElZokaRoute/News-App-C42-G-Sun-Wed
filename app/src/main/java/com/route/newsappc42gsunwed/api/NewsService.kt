package com.route.newsappc42gsunwed.api

import com.route.newsappc42gsunwed.api.model.NewsResponse
import com.route.newsappc42gsunwed.api.model.SourcesResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("sources")
    suspend fun getSources(
        @Query("category") categoryApiID: String? = null
    ): Response<SourcesResponse>

    // enqueue    /   execute
    //    Compatible with Kotlin Coroutines
    //   Pause / Resume Technique
    @GET("everything")
    suspend fun getNewsBySource(
        @Query("sources") sourceId: String? = null,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = 10
    ): Response<NewsResponse>   //  NewsResponse or Response<NewsResponse>
}
