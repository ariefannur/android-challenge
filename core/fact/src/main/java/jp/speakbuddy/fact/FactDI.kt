package jp.speakbuddy.fact

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.speakbuddy.fact.database.FactDatabase
import jp.speakbuddy.fact.datasource.FactLocal
import jp.speakbuddy.fact.datasource.FactLocalDataSource
import jp.speakbuddy.fact.datasource.FactRemote
import jp.speakbuddy.fact.domain.GetHistory
import jp.speakbuddy.fact.domain.GetRandomFact
import jp.speakbuddy.fact.network.FactService
import jp.speakbuddy.fact.repository.FactRepository
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FactDI {
    @Provides
    @Singleton
    fun provideFactService(client: OkHttpClient): FactService {
            return Retrofit.Builder()
                .baseUrl("https://catfact.ninja/")
                .client(client)
                .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
                .build()
                .create(FactService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): FactDatabase {
        return Room.databaseBuilder(
            context,
            FactDatabase::class.java,
            "db-fact").build()
    }

    private fun provideLocalDataSource(
        db: FactDatabase
    ): FactLocalDataSource {
        return FactLocal(db.factDao())
    }

    @Provides
    @Singleton
    fun provideFactRepository(service: FactService, db: FactDatabase): FactRepository {
        return FactRepository(provideLocalDataSource(db), FactRemote(service))
    }

    @Provides
    @Singleton
    fun provideGetRandomFact(repo: FactRepository) = GetRandomFact(repo)

    @Provides
    @Singleton
    fun provideGetHistory(repo: FactRepository) = GetHistory(repo)

}