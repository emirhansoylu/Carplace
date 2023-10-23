package dev.duckbuddyy.carplace.network_retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

internal object NetworkModule {
    private val client by lazy {
        OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG)
                    HttpLoggingInterceptor.Level.BODY
                else
                    HttpLoggingInterceptor.Level.NONE
            }).build()
    }

    private val retrofit by lazy {
        val contentType = "application/json".toMediaType()
        Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory(contentType))
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .build()
    }

    val apiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}