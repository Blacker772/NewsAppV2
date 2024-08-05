package com.example.worldnews.repository

import androidx.lifecycle.LiveData
import com.example.worldnews.Constants
import com.example.worldnews.data.database.DAO
import com.example.worldnews.data.database.DatabaseModel
import com.example.worldnews.data.response.ApiService
import com.example.worldnews.data.responsemodel.Articles
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val apiService: ApiService,
    private val dao: DAO
) {

    suspend fun getNewsRepo(): Flow<List<Articles>> = flow {
        val result = apiService.getNews(Constants.COUNTRY, Constants.API_KEY)
        if (result.isSuccessful) {
            result.body().let {
                it?.articles
            }?.let { emit(it) } ?: throw Exception("Response is null")
        } else {
            throw Exception("Error ${result.code()}")
        }
    }

    suspend fun getNewsByCategoryRepo(category: String): Flow<List<Articles>> = flow {
        val category = apiService.getNewsByCategory(category, Constants.API_KEY, Constants.COUNTRY)
        if (category.isSuccessful) {
            category.body().let {
                it?.articles
            }?.let { emit(it) } ?: throw Exception("Response is null")
        }else{
            throw Exception("Error ${category.code()}")
        }
    }

    suspend fun getSearchNewsRepo(query: String): Flow<List<Articles>> = flow {
        val search = apiService.getSearchNews(query, Constants.API_KEY, Constants.COUNTRY)
        if (search.isSuccessful) {
            search.body().let {
                it?.articles
            }?.let { emit(it) } ?: throw Exception("Response is null")
        }else{
            throw Exception("Error ${search.code()}")
        }
    }

    val allSavedNews: LiveData<List<DatabaseModel>> = dao.getAllData()

    suspend fun insert(data: DatabaseModel) {
        dao.insertData(data)
    }

    fun getLinkData(link: String): String {
        return dao.getDataLink(link)
    }

    fun delete() {
        dao.clearData()
    }
}
