package com.example.worldnews.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface DAO {

    //Функция для добавления данных в базу
    @Insert
    suspend fun insertData(data: DatabaseModel)

    //Функция для получения всех данных из базы
    @Query("SELECT * FROM table_for_saveFragment")
    fun getAllData(): LiveData<List<DatabaseModel>>

    //Функция для удаления данных из базы
    @Query("DELETE FROM table_for_saveFragment")
    fun clearData()

    //Функция для получения данных по ссылке
    @Query("SELECT link FROM table_for_saveFragment WHERE link = :link")
    fun getDataLink(link: String): String
}