package com.ramonaharrison.mininytimes

data class NewsStory(
    val headline: String,
    val summary: String,
    val imageUrl: String?,
    val clickUrl: String
)
