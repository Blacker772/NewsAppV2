package com.example.worldnews.ui

import com.example.worldnews.data.emilnews.NewsItem
import com.example.worldnews.data.responsemodel.Articles
import com.example.worldnews.data.responsemodel.NewsModel

sealed class UiState{
    data class Error(val message: String?) :UiState()
    data class Loading(val isLoading: Boolean) : UiState()
    data class Data(val data: List<Articles>, var isLoading: Boolean) : UiState()
    data object None: UiState()
}