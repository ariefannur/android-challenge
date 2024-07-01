package jp.speakbuddy.fact.datasource

import jp.speakbuddy.fact.network.FactResponse
import jp.speakbuddy.fact.network.FactService

class FactRemote(private val service: FactService): FactRemoteDataSource {
    override suspend fun fetchFact(): FactResponse {
        return service.getFact()
    }
}