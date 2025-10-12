package com.route.data.mapper.news

import com.route.data.models.news.ArticlesItemModel
import com.route.data.models.news.SourcesItemModel
import com.route.domain.entities.news.ArticlesItemEntity
import com.route.domain.entities.news.SourcesItemEntity

fun SourcesItemModel.toEntity() =
    SourcesItemEntity(country, name, description, language, id, category, url)

fun SourcesItemEntity.toModel() =
    SourcesItemModel(country, name, description, language, id, category, url)

fun ArticlesItemModel.toEntity() =
    ArticlesItemEntity(publishedAt, author, urlToImage, description, title, url, content)
