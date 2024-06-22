package jp.speakbuddy.image.network

import jp.speakbuddy.core.image.BuildConfig
import kotlinx.serialization.Serializable
import retrofit2.http.GET
import retrofit2.http.Headers

interface ImageService {
    @Headers("Authorization: ${BuildConfig.IMAGE_TOKEN}")
    @GET("search?query=cat")
    suspend fun getCatImage(): ImageResponse
}

@Serializable
data class ImageResponse(
    val photos: List<Photos>
)
@Serializable
data class Photos(
    val src: Src
)
@Serializable
data class Src(
    val medium: String,
    val large: String,
    val small: String,
)