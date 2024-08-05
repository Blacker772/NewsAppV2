package com.example.worldnews.data.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

//Название моей таблицы в базе
@Entity(tableName = "table_for_saveFragment")
@Parcelize
data class DatabaseModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,

    @ColumnInfo
    val title: String?,

    @ColumnInfo
    val link: String?,

    @ColumnInfo
    val content: String?,

    @ColumnInfo
    val pubDate: String?,

    @ColumnInfo
    val imageUrl: String?,

    @ColumnInfo
    val sourceId: String?
):Parcelable