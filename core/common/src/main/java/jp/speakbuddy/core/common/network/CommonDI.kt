package jp.speakbuddy.core.common.network

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.speakbuddy.core.common.BuildConfig
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object CommonDI {
    @Provides
    @Singleton
    fun provideClient(@ApplicationContext context: Context): OkHttpClient {
        val builder =  OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                builder.addInterceptor(ChuckerInterceptor(context))
            }
        return builder.build()
    }
}