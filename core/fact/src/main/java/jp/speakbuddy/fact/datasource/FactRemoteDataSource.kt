package jp.speakbuddy.fact.datasource

import jp.speakbuddy.fact.network.FactResponse

interface FactRemoteDataSource {
    suspend fun fetchFact() : FactResponse

}