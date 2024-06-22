package jp.speakbuddy.image.datasource

import jp.speakbuddy.image.network.Photos

interface ImageRemoteDataSource {
    suspend fun fetchImage(): List<Photos>
}