package com.example.worldnews.ui.save.allsaved

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.worldnews.data.database.DatabaseModel
import com.example.worldnews.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel

import javax.inject.Inject

@HiltViewModel
class SaveViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val state: LiveData<List<DatabaseModel>> = repository.allSavedNews

    suspend fun insert(data: DatabaseModel) {
        repository.insert(data)
    }

    fun getLinkData(link: String): String {
        return repository.getLinkData(link)
    }

    fun delete() {
        repository.delete()
    }

}