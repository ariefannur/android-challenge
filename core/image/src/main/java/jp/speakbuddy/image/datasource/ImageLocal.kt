package jp.speakbuddy.image.datasource

import android.content.Context
import android.util.Log
import jp.speakbuddy.image.local.dataStoreImage
import jp.speakbuddy.image.local.getImageData
import jp.speakbuddy.image.local.saveImageData
import jp.speakbuddy.image.network.Photos
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

class ImageLocal(context: Context): ImageLocalDataSource {
    private val dataStore by lazy { context.dataStoreImage }
    override suspend fun getImage(): Flow<List<Photos>> = channelFlow {
        getImageData(dataStore).collectLatest {
            val listPhoto =  if (it.isEmpty()) {
                listOf()
            } else {
                Json.decodeFromString<List<Photos>>(it)
            }
            send(listPhoto)
        }
    }

    override suspend fun cacheImage(photos: List<Photos>) {
        val serializer = ListSerializer(Photos.serializer())
        val imageData = Json.encodeToString(serializer, photos)
        saveImageData(dataStore, imageData)
    }
}