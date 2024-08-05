package com.example.worldnews.data.responsemodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.net.URL
import javax.inject.Inject

//данные с API
@Parcelize
data class Articles @Inject constructor(
    val title: String,
    val description: String,
    val content: String,
    val url: String,
    val image: String,
    val publishedAt: String,
    val source: Source
):Parcelable
