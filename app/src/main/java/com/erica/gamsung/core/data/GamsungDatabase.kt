package com.erica.gamsung.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.erica.gamsung.menu.data.local.MenuDao
import com.erica.gamsung.menu.data.local.MenuEntity
import com.erica.gamsung.store.data.local.StoreDao
import com.erica.gamsung.store.data.local.StoreEntity

@Database(entities = [MenuEntity::class, StoreEntity::class], version = 1)
@TypeConverters(DatabaseConverters::class)
abstract class GamsungDatabase : RoomDatabase() {
    abstract fun menuDao(): MenuDao

    abstract fun storeDao(): StoreDao
}
