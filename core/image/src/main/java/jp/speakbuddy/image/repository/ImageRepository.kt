package jp.speakbuddy.image.repository

import jp.speakbuddy.image.datasource.ImageLocalDataSource
import jp.speakbuddy.image.datasource.ImageRemoteDataSource
import jp.speakbuddy.image.network.Photos
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.withContext

class ImageRepository(
    private val local: ImageLocalDataSource,
    private val remote: ImageRemoteDataSource,
) {

    private suspend fun getImage(): Flow<List<Photos>> = channelFlow {
        local.getImage().collect {
            withContext(currentCoroutineContext()) {
                if (it.isEmpty()) {
                    val remoteData = remote.fetchImage()
                    kotlin.runCatching {
                        local.cacheImage(remoteData)
                    }
                    send(remoteData)
                } else {
                    send(it)
                }
            }
        }
    }
    suspend fun getRandomImage(): Flow<Photos> = channelFlow {
        getImage().collect {
            withContext(currentCoroutineContext()) {
                val random = it.indices.random()
                send(it[random])
            }
        }
    }


}