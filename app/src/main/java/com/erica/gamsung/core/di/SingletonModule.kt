package com.erica.gamsung.core.di

import com.erica.gamsung.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SingletonModule {
    private const val BASE_URL = BuildConfig.SERVER_BASE_URL

    val retrofit: Retrofit =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}
