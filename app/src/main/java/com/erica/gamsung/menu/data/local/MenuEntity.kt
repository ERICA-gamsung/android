package com.erica.gamsung.menu.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.erica.gamsung.menu.domain.Menu

@Entity(tableName = "menus")
data class MenuEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val price: Int,
) {
    fun toDomainModel(): Menu =
        Menu(
            name = name,
            price = price,
        )
}
