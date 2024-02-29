package com.erica.gamsung.menu.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "menus")
data class MenuEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val price: Int,
)
