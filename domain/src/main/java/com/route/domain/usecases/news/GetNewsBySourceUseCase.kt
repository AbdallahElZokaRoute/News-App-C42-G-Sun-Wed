package com.route.domain.usecases.news

import com.route.domain.repositories.news.NewsRepository
import javax.inject.Inject

class GetNewsBySourceUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend fun invoke(sourceId: String) = repository.getNewsBySource(sourceId)
}

// Pause / Resume
