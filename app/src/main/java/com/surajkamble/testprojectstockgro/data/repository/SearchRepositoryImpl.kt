package com.surajkamble.testprojectstockgro.data.repository

import com.surajkamble.testprojectstockgro.data.repository.util.SearchResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch


val sourceA = listOf("a", "b", "c")
val sourceB = listOf("ea", "dc", "b", "dkica")
val sourceC = listOf("svfdsfv", "jfvb", "aac")

val allSources = listOf(sourceA, sourceB, sourceC)

class SearchRepositoryImpl {
    fun searchAllSources(query: String): Flow<SearchResult> = channelFlow {
        allSources.forEachIndexed { index, source ->
            launch {
                if(index == 1){
                    delay(1000)
                }
                if(index == 2){
                    delay(800)
                }
                val results = source.filter { it.contains(query) }
                send(SearchResult(index, results))
            }
        }
    }
}