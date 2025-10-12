package com.route.newsappc42gsunwed.ui.screens.news

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.route.domain.entities.news.ArticlesItemEntity
import com.route.domain.entities.news.SourcesItemEntity
import com.route.domain.usecases.news.GetNewsBySourceUseCase
import com.route.domain.usecases.news.GetSourcesUseCase
import com.route.domain.utils.base.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getSourcesUseCase: GetSourcesUseCase,
    private val getNewsBySourceUseCase: GetNewsBySourceUseCase,
) : ViewModel() {
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
    //    val repository: NewsRepository = NewsRepository()
    val sourcesResource = mutableStateOf<Resource<List<SourcesItemEntity>>>(Resource.Initial())
    val articlesResource = mutableStateOf<Resource<List<ArticlesItemEntity>>>(Resource.Initial())

    fun getSources(categoryApiId: String) {
        viewModelScope.launch(context = Dispatchers.IO) {
            sourcesResource.value = Resource.Loading()
            val response = getSourcesUseCase.invoke(categoryApiId)
            sourcesResource.value = response
        }
    }

    fun getNewsBySourceId(sourceId: String) {
        viewModelScope.launch {
            articlesResource.value = Resource.Loading()
            val response = getNewsBySourceUseCase.invoke(sourceId)
            articlesResource.value = response
        }
    }
}
