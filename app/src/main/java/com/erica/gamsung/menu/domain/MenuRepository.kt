package com.erica.gamsung.menu.domain

import kotlinx.coroutines.flow.Flow

interface MenuRepository {
    suspend fun updateMenus(
        menus: List<Menu>,
        userId: Long,
    )

    suspend fun getMenus(userId: Long): Flow<List<Menu>>
}
