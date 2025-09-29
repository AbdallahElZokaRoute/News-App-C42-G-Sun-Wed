package com.route.newsappc42gsunwed.ui.screens.news

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.route.newsappc42gsunwed.api.ApiManager
import com.route.newsappc42gsunwed.api.model.ArticlesItemDM
import com.route.newsappc42gsunwed.api.model.NewsResponse
import com.route.newsappc42gsunwed.api.model.SourcesItemDM
import com.route.newsappc42gsunwed.api.model.SourcesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    val sourcesList = mutableStateListOf<SourcesItemDM>()
    val sourcesError = mutableStateOf("")
    val selectedSourceId = MutableStateFlow<String>("")
    val articlesList = mutableStateListOf<ArticlesItemDM>()
    val articlesError = mutableStateOf("")
    fun getSources(categoryApiId: String) {
        viewModelScope.launch(context = Dispatchers.IO) {
            try {
                val response = ApiManager.getNewsService().getSources(categoryApiID = categoryApiId)
                if (response.isSuccessful) {
                    val sources = response.body()?.sources ?: listOf()
                    sourcesList.addAll(sources)
                } else {
                    val errorBody = response.errorBody()?.string()
                    val gson = Gson()
                    val sourcesResponse = gson.fromJson(errorBody, SourcesResponse::class.java)
                    sourcesError.value = sourcesResponse.message ?: "Something went wrong"
                }
            } catch (e: Exception) {
                sourcesError.value = e.message ?: "Something went wrong"
            }
        }
    }

    fun getNewsBySourceId(sourceId: String) {
        viewModelScope.launch {
            try {
                val response = ApiManager.getNewsService().getNewsBySource(sourceId, 1, 15)
                if (response.isSuccessful) {
                    val articles = response.body()?.articles ?: listOf()
                    articlesList.clear()
                    articlesList.addAll(articles)
                } else {
                    val errorBody = response.errorBody()?.string()
                    val gson = Gson()
                    val newsResponse = gson.fromJson(errorBody, NewsResponse::class.java)
                    articlesError.value = newsResponse.message ?: "Something went wrong"
                }
            } catch (e: Exception) {
                articlesError.value = e.message ?: "Something went wrong"
            }
        }
    }
}
