package com.route.domain.entities.news

data class SourcesResponseEntity(
    val sources: List<SourcesItemEntity>? = null,
    val message: String? = null,
    val status: String? = null
)

data class SourcesItemEntity(

    val country: String? = null,
    val name: String? = null,
    val description: String? = null,
    val language: String? = null,
    var id: String = "",
    val category: String? = null,
    val url: String? = null
)
