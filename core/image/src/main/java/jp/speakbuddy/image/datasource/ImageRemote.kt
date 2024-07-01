package jp.speakbuddy.image.datasource

import jp.speakbuddy.image.network.ImageService
import jp.speakbuddy.image.network.Photos

class ImageRemote(private val service: ImageService): ImageRemoteDataSource {
    override suspend fun fetchImage(): List<Photos> {
        return service.getCatImage().photos
    }
}