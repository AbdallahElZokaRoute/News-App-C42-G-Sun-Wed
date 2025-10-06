package com.route.newsappc42gsunwed.ui.screens.news

import android.content.Context
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
import com.route.newsappc42gsunwed.ui.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    val selectedSourceId = MutableStateFlow<String>("")

    // Caching   1- (Networking Library -> Retrofit (Search) )
    //           2- Room

    //           call APIs and Cache in View Model


    // Important ->  Holds a State for the View

    //  View -> View Model  -> Repository -> RemoteDataSource (APIs)
    //                      (No Data at all )
    //                      ( Call API and Store SourcesItemDM )      -> LocalDataSource  (Room)
    //                      (Facebook) ->
    // (2 endpoints )
    val repository: NewsRepository = NewsRepository()
    val sourcesResource = mutableStateOf<Resource<List<SourcesItemDM>>>(Resource.Initial())
    val articlesResource = mutableStateOf<Resource<List<ArticlesItemDM>>>(Resource.Initial())

    fun getSources(categoryApiId: String, context: Context) {
        viewModelScope.launch(context = Dispatchers.IO) {
            sourcesResource.value = Resource.Loading()
            val response = repository.getSources(categoryApiId, context)
            sourcesResource.value = response
        }
    }

    fun getNewsBySourceId(sourceId: String) {
        viewModelScope.launch {
            articlesResource.value = Resource.Loading()
            try {
                val response = ApiManager.getNewsService().getNewsBySource(sourceId, 1, 15)
                if (response.isSuccessful) {
                    val articles = response.body()?.articles ?: listOf()
                    articlesResource.value = Resource.Success(articles)
                } else {
                    val errorBody = response.errorBody()?.string()
                    val gson = Gson()
                    val newsResponse = gson.fromJson(errorBody, NewsResponse::class.java)
                    articlesResource.value =
                        Resource.Error(newsResponse.message ?: "Something went wrong")
                }
            } catch (e: Exception) {
                articlesResource.value = Resource.Error(e.message ?: "Something went wrong")
            }
        }
    }
}
