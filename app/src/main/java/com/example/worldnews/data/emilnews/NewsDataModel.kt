package com.example.worldnews.data.emilnews

import javax.inject.Inject

data class NewsDataModel @Inject constructor(
    val news: List<NewsItem>
)
