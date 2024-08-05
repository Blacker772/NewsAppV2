package com.example.worldnews.data.emilnews

import com.google.gson.annotations.SerializedName
import javax.inject.Inject

data class NewsItem @Inject constructor(
    val id: Int,
    val title: String,
    @SerializedName("excerpt")
    val source: String,
    @SerializedName("title_image_medium")
    val image: String,
    @SerializedName("date_smart")
    val time: String,
    val date: String
)
