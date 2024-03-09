package com.erica.gamsung.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.erica.gamsung.menu.data.local.MenuDao
import com.erica.gamsung.menu.data.local.MenuEntity

@Database(entities = [MenuEntity::class], version = 1)
abstract class GamsungDatabase : RoomDatabase() {
    abstract fun menuDao(): MenuDao
}
