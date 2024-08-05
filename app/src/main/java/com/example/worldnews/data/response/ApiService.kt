package com.example.worldnews.data.response

import com.example.worldnews.data.responsemodel.Articles
import com.example.worldnews.data.responsemodel.NewsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api/v4/top-headlines")
    suspend fun getNews(
        @Query("country") country: String?,
        @Query("apikey") key: String?,
    ): Response<NewsModel>

    @GET("api/v4/search/")
    suspend fun getSearchNews(
        @Query("q") search:String?,
        @Query("apikey") key: String?,
        @Query("country") country: String?
    ):Response<NewsModel>

    @GET("api/v4/top-headlines")
    suspend fun getNewsByCategory(
        @Query("category") category:String?,
        @Query("apikey") key: String?,
        @Query("country") country: String?
    ):Response<NewsModel>
}