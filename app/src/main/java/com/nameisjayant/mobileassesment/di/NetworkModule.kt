package com.nameisjayant.mobileassesment.di

import com.nameisjayant.mobileassesment.BuildConfig
import com.nameisjayant.mobileassesment.data.network.ApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesMoshi(): Moshi =
        Moshi
            .Builder()
            .run {
                add(KotlinJsonAdapterFactory())
                build()
            }
    @Provides
    @Singleton
    fun providesApiService(
        moshi: Moshi,
        client: OkHttpClient,
    ): ApiService = retrofit {
        baseUrl(BuildConfig.BASE_URL)
        addConverterFactory(MoshiConverterFactory.create(moshi))
        client(client)
        build()
    }.create(ApiService::class.java)
    @Provides
    @Singleton
    fun providesOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .also { client ->
                client.addInterceptor(HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                })
            }
            .build()
    }
    private inline fun retrofit(init: Retrofit.Builder.() -> Unit): Retrofit {
        val retrofit = Retrofit.Builder()
        retrofit.init()
        return retrofit.build()
    }
}