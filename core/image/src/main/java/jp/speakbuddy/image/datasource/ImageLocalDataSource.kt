package jp.speakbuddy.image.datasource

import jp.speakbuddy.image.network.Photos
import kotlinx.coroutines.flow.Flow

interface ImageLocalDataSource {
    suspend fun getImage(): Flow<List<Photos>>
    suspend fun cacheImage(photos: List<Photos>)
}