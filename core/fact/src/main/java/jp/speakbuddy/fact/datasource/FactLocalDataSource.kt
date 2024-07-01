package jp.speakbuddy.fact.datasource

import jp.speakbuddy.fact.network.FactResponse

interface FactLocalDataSource {
    suspend fun getLastFact(): FactResponse?
    suspend fun cacheFact(fact: FactResponse)
    suspend fun getListHistory(filter: String): List<FactResponse>
}