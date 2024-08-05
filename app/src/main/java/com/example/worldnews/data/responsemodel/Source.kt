package com.example.worldnews.data.responsemodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import javax.inject.Inject
@Parcelize
data class Source @Inject constructor(
    val name: String?,
    val url: String?
): Parcelable
