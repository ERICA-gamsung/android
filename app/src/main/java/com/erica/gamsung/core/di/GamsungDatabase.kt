package com.erica.gamsung.core.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.erica.gamsung.menu.data.local.MenuDao
import com.erica.gamsung.menu.data.local.MenuEntity
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [MenuEntity::class], version = 1)
abstract class GamsungDatabase : RoomDatabase() {
    abstract fun menuDao(): MenuDao

    companion object {
        private var instance: GamsungDatabase? = null

        @OptIn(InternalCoroutinesApi::class)
        @Synchronized
        fun getInstance(context: Context): GamsungDatabase? {
            if (instance == null) {
                synchronized(GamsungDatabase::class) {
                    instance =
                        Room
                            .databaseBuilder(
                                context.applicationContext,
                                GamsungDatabase::class.java,
                                "gamsung-database",
                            ).build()
                }
            }
            return instance
        }
    }
}
