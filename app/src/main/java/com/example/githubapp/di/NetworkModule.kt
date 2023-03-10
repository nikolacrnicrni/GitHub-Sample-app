package com.example.githubapp.di

import com.example.githubapp.data.remote.ApiService
import com.example.githubapp.util.Constants
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideHttpLoggerInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun provideCallFactory(httpLoggingInterceptor: HttpLoggingInterceptor): Call.Factory {
        return OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()
    }

    @Singleton
    @Provides
    fun provideRxJavaCallAdapterFactory(): RxJava3CallAdapterFactory {
        return RxJava3CallAdapterFactory.create()
    }

    @Singleton
    @Provides
    fun provideGsonBuilder(): GsonBuilder {
        return GsonBuilder().setLenient()
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(gsonBuilder: GsonBuilder): GsonConverterFactory {
        return GsonConverterFactory.create(gsonBuilder.create())
    }

    @RepoApi
    @Singleton
    @Provides
    fun provideGitHubApi(
        httpLoggingInterceptor: Call.Factory,
        rxJava3CallAdapterFactory: RxJava3CallAdapterFactory,
        gsonConverterFactory: GsonConverterFactory
    ): ApiService {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .callFactory(httpLoggingInterceptor)
            .addCallAdapterFactory(rxJava3CallAdapterFactory)
            .build().create(ApiService::class.java)
    }
    @LoginApi
    @Singleton
    @Provides
    fun provideGitLoginApi(
        httpLoggingInterceptor: Call.Factory,
        rxJava3CallAdapterFactory: RxJava3CallAdapterFactory,
        gsonConverterFactory: GsonConverterFactory
    ): ApiService {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL_LOGIN)
            .addConverterFactory(gsonConverterFactory)
            .callFactory(httpLoggingInterceptor)
            .addCallAdapterFactory(rxJava3CallAdapterFactory)
            .build().create(ApiService::class.java)
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LoginApi

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RepoApi
