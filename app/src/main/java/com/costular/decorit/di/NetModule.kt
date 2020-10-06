package com.costular.decorit.di

import com.costular.decorit.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class NetModule {

    @Unsplash
    @Singleton
    @Provides
    fun providesOkHttpClientForUnsplash(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor {
                val request = it.request().newBuilder()
                    .addHeader("Accept-Version", "v1")
                    .addHeader("Authorization", "Client-ID ${BuildConfig.UNSPLASH_ACCESS_KEY}")
                    .build()

                it.proceed(request)
            }
            .build()

    @Unsplash
    @Singleton
    @Provides
    fun providesRetrofitForUnsplash(@Unsplash client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

}