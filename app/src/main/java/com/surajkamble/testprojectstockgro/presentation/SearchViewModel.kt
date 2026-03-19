package com.surajkamble.testprojectstockgro.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.surajkamble.testprojectstockgro.domain.SearchUseCase
import com.surajkamble.testprojectstockgro.presentation.util.SearchUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(private val searchUseCase: SearchUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    private var searchJob: Job? = null
    private val totalSourcesCount = 3

    fun onSearchClicked(query: String) {
        if (query.isBlank()) return

        searchJob?.cancel()

        _uiState.value = SearchUiState(isLoading = true)
        val collectedResults = mutableSetOf<String>()
        var completedSources = 0

        searchJob = viewModelScope.launch {
            searchUseCase(query).collect { result ->
                completedSources++
                collectedResults.addAll(result.matches)

                _uiState.update { it.copy(
                    results = collectedResults.toList(),
                    isLoading = completedSources < totalSourcesCount
                )}
            }
        }
    }

    fun onCancelClicked() {
        searchJob?.cancel()
        _uiState.update { it.copy(isLoading = false) }
    }

    companion object {
        fun provideFactory(searchUseCase: SearchUseCase): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                return SearchViewModel(searchUseCase) as T
            }
        }
    }
}
