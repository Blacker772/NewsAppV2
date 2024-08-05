package com.example.worldnews.data.responsemodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

//модель данных, которую я получаю с API
@Parcelize
data class NewsModel @Inject constructor(
    val totalArticles: Int?,
    val articles: List<Articles>    //данные с API приходят в виде листа(articles[])
): Parcelable


