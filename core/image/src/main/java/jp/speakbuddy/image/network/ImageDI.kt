package jp.speakbuddy.image.network

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.speakbuddy.image.datasource.ImageLocal
import jp.speakbuddy.image.datasource.ImageRemote
import jp.speakbuddy.image.domain.GetRandomImage
import jp.speakbuddy.image.repository.ImageRepository
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImageDI {

    private val json = Json { ignoreUnknownKeys = true }

    @Provides
    @Singleton
    fun provideImageService(client: OkHttpClient): ImageService {
        return Retrofit.Builder()
            .baseUrl("https://api.pexels.com/v1/")
            .client(client)
            .addConverterFactory(
                    json.asConverterFactory("application/json".toMediaType())
            )
            .build()
            .create(ImageService::class.java)
    }

    @Provides
    @Singleton
    fun provideImageRepository(@ApplicationContext context: Context, imageService: ImageService): ImageRepository {
        return ImageRepository(ImageLocal(context), ImageRemote(imageService))
    }

    @Provides
    @Singleton
    fun provideGetRandomImage(repo: ImageRepository): GetRandomImage {
        return GetRandomImage(repo)
    }
}