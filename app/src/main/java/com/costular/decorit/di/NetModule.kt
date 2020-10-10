package com.costular.decorit.di

import android.content.Context
import com.chuckerteam.chucker.api.Chucker
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.costular.decorit.BuildConfig
import com.costular.decorit.core.net.AppDispatcherProvider
import com.costular.decorit.core.net.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class NetModule {
    
    @Provides
    @Singleton
    fun providesAppDispatcherProvider(): DispatcherProvider = AppDispatcherProvider()

    @Provides
    @Singleton
    fun providesChuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor =
        ChuckerInterceptor(context)

    @Unsplash
    @Singleton
    @Provides
    fun providesOkHttpClientForUnsplash(chuckerInterceptor: ChuckerInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(chuckerInterceptor)
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
            .baseUrl("https://api.unsplash.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

}