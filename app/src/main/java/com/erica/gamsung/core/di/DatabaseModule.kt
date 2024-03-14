package com.erica.gamsung.core.di

import android.content.Context
import androidx.room.Room
import com.erica.gamsung.core.data.GamsungDatabase
import com.erica.gamsung.menu.data.local.MenuDao
import com.erica.gamsung.store.data.local.StoreDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Singleton
    @Provides
    fun provideGamsungDatabase(
        @ApplicationContext context: Context,
    ): GamsungDatabase =
        Room
            .databaseBuilder(
                context,
                GamsungDatabase::class.java,
                "gamsung-database",
            ).build()

    @Singleton
    @Provides
    fun provideMenuDao(database: GamsungDatabase): MenuDao = database.menuDao()

    @Singleton
    @Provides
    fun provideStoreDao(database: GamsungDatabase): StoreDao = database.storeDao()
}
