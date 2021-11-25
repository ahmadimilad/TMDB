package com.tmdb.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tmdb.BuildConfig
import com.tmdb.data.remote.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class ApplicationInterceptors

    //===================================================================
    @Singleton
    @Provides
    @ApplicationInterceptors
    fun provideApplicationInterceptors(): ArrayList<Interceptor> {
        val interceptors = arrayListOf<Interceptor>()

        //region Add API_KEY
        val keyInterceptor = Interceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url

            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY)
                .build()

            val request = original.newBuilder()
                .url(url)
                .build()

            return@Interceptor chain.proceed(request)
        }

        interceptors.add(keyInterceptor)
        //endregion

        //region Logging
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        }

        interceptors.add(httpLoggingInterceptor)
        //endregion

        return interceptors
    }

    //===================================================================
    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationInterceptors applicationInterceptors: ArrayList<Interceptor>
    ): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
//            .connectTimeout(30, TimeUnit.SECONDS)
//            .readTimeout(30, TimeUnit.SECONDS)
//            .writeTimeout(30, TimeUnit.SECONDS)

        //region ApplicationInterceptors = {Headers, Authorization, Log, ...}
        applicationInterceptors.forEach { interceptor ->
            httpClient.addInterceptor(interceptor)
        }
        //endregion

        return httpClient.build()
    }

    //===================================================================
    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.serializeNulls()
        return gsonBuilder.create()
    }

    //===================================================================
    @Singleton
    @Provides
    fun provideRetrofit(
        gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(okHttpClient)
            .build()
    }

    //===================================================================
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}