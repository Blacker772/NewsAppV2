package com.example.worldnews.di

import com.example.worldnews.Constants
import com.example.worldnews.data.response.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//модуль для Retrofit
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit(gsonConverterFactory: GsonConverterFactory,
                         okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun providesGson(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun providesOkhttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
//        interceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
//            .addInterceptor(interceptor)
            .build()
    }

//    @Singleton
//    @Provides
//    fun providesLanguageKey(): Interceptor {
//        val interceptor = Interceptor { chain ->
//            val url = chain.request()
//                .url
//                .newBuilder()
//                .addQueryParameter("lang", "ru")
//                .build()
//            val request = chain.request()
//                .newBuilder()
//                .addHeader("Authorization", "Bearer ${Constants.API_KEY_2}")
//                .url(url)
//                .build()
//            return@Interceptor chain.proceed(request)
//        }
//        return interceptor
//    }

    @Singleton
    @Provides
    fun providesHttpLogging(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }


    @Singleton
    @Provides
    fun providesApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
