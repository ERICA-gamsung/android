package com.erica.gamsung.menu.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface MenuDao {
    @Query("DELETE FROM menus")
    fun deleteAll()

    @Insert
    fun insertAll(vararg menus: MenuEntity)

    @Suppress("SpreadOperator")
    @Transaction
    fun updateAll(menus: List<MenuEntity>) {
        deleteAll()
        insertAll(*menus.toTypedArray())
    }

    @Query("SELECT * FROM menus")
    fun getAll(): List<MenuEntity>
}
