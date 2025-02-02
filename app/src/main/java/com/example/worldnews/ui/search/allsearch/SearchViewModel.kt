package com.example.worldnews.ui.search.allsearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldnews.repository.Repository
import com.example.worldnews.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableStateFlow<UiState>(UiState.None)
    val state: StateFlow<UiState> = _state

    suspend fun getSearchNews(search: String) {
        viewModelScope.launch {
            repository.getSearchNewsRepo(search)
                .onStart {
                    _state.value = UiState.Loading(true)
                }
                .catch { e ->
                    _state.value = UiState.Error(e.message)
                }
                .collect {
                    _state.value = UiState.Data(it, false)
                }
        }
    }

    suspend fun getNews() {
        viewModelScope.launch {
            repository.getNewsRepo()
                .onStart {
                    _state.value =UiState.Loading(true)
                }
                .catch {
                    e -> _state.value = UiState.Error(e.message)
                }
                .collect {
                    _state.value = UiState.Data(it, false)
                }
        }
    }
}

