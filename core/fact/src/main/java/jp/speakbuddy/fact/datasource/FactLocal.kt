package jp.speakbuddy.fact.datasource

import android.util.Log
import jp.speakbuddy.fact.database.FactDao
import jp.speakbuddy.fact.mapper.toFactEntity
import jp.speakbuddy.fact.network.FactResponse

class FactLocal(
    private val factDao: FactDao
): FactLocalDataSource {
    override suspend fun getLastFact(): FactResponse? {
        return factDao.getAllFact().let {
            if (it.isEmpty()) null else {
                it.first().toFactResponse()
            }
        }
    }
    override suspend fun cacheFact(fact: FactResponse) {
        factDao.insertFact(fact.toFactEntity())
    }

    override suspend fun getListHistory(filter: String): List<FactResponse> {
        return if (filter.isEmpty())
            factDao.getAllFact().map { it.toFactResponse() }
        else {
            factDao.searchFacts("%${filter}%")?.map { it.toFactResponse() } ?: listOf()
        }
    }
}