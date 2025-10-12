package com.route.domain.usecases.news

import com.route.domain.entities.news.SourcesItemEntity
import com.route.domain.repositories.news.NewsRepository
import com.route.domain.utils.base.Resource
import javax.inject.Inject

class GetSourcesUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend fun invoke(categoryId: String) = repository.getSources(categoryId)
}
