package com.surajkamble.testprojectstockgro.presentation.util

data class SearchUiState(
    val results: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val isNoResultsFound: Boolean = false,
    val errorMessage: String? = null
)
