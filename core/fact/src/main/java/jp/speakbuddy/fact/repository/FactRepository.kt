package jp.speakbuddy.fact.repository

import android.util.Log
import jp.speakbuddy.fact.datasource.FactLocalDataSource
import jp.speakbuddy.fact.datasource.FactRemoteDataSource
import jp.speakbuddy.fact.domain.model.Fetching
import jp.speakbuddy.fact.network.FactResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FactRepository(
    private val local: FactLocalDataSource,
    private val remote: FactRemoteDataSource
) {
    suspend fun getRandomFact(fetching: Fetching, length: Int = 0): Flow<FactResponse> = flow  {
        when(fetching) {
            Fetching.LOCAL -> {
                getLocal(length)?.let { emit(it) } ?: emit(FactResponse("", 0))
            }
            Fetching.LOCAL_AN_UPDATE -> {
                val localData = getLocal(length)
                if (localData == null) {
                    emit(fetchRemote(length))
                } else {
                    emit(localData)
                    emit(fetchRemote(length))
                }
            }
            Fetching.REMOTE -> {
                emit(fetchRemote(length))
            }
        }


    }

    private suspend fun getLocal(length: Int): FactResponse? {
        val localData = local.getLastFact()
        Log.d("AF", "Local : $localData")
        return if (length == 0 || (length > 0 && (localData?.length ?: 0) <= length)) {
            localData
        } else if (localData == null) null else getLocal(length)
    }

    private suspend fun fetchRemote(length: Int): FactResponse {
        val remoteData = remote.fetchFact()
        kotlin.runCatching { local.cacheFact(remoteData) }
        return if (length == 0 || (length > 0 && remoteData.length <= length)) {
            remoteData
        } else fetchRemote(length)
    }

    fun getListHistory(filter: String): Flow<List<FactResponse>> = flow {
        emit(local.getListHistory(filter))
    }
}