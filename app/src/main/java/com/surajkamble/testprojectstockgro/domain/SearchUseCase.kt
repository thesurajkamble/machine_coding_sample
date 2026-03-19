package com.surajkamble.testprojectstockgro.domain

import com.surajkamble.testprojectstockgro.data.repository.SearchRepositoryImpl
import com.surajkamble.testprojectstockgro.data.repository.util.SearchResult
import kotlinx.coroutines.flow.Flow

class SearchUseCase(
    private val searchRepository: SearchRepositoryImpl
) {
    operator fun invoke(query: String): Flow<SearchResult> {
        return searchRepository.searchAllSources(query)
    }
}

