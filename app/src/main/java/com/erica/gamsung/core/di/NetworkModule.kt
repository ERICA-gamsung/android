package com.erica.gamsung.core.di

import com.erica.gamsung.BuildConfig
import com.erica.gamsung.menu.data.remote.MenuApi
import com.erica.gamsung.post.data.remote.PostApi
import com.erica.gamsung.store.data.remote.StoreApi
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.JsonSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = BuildConfig.SERVER_BASE_URL

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val localTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")

        val gson =
            GsonBuilder()
                .registerTypeAdapter(
                    LocalTime::class.java,
                    JsonDeserializer { json, _, _ ->
                        LocalTime.parse(json.asJsonPrimitive.asString, localTimeFormatter)
                    },
                ).registerTypeAdapter(
                    LocalTime::class.java,
                    JsonSerializer<LocalTime> { src, _, _ ->
                        GsonBuilder().create().toJsonTree(src.format(localTimeFormatter))
                    },
                ).create()

        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    fun provideMenuApi(retrofit: Retrofit): MenuApi = retrofit.create(MenuApi::class.java)

    @Singleton
    @Provides
    fun provideStoreApi(retrofit: Retrofit): StoreApi = retrofit.create(StoreApi::class.java)

    @Singleton
    @Provides
    fun providePostApi(retrofit: Retrofit): PostApi = retrofit.create(PostApi::class.java)
}
