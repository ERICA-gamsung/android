package com.erica.gamsung.menu.domain

import kotlinx.coroutines.flow.Flow

interface MenuRepository {
    suspend fun updateMenus(menus: List<Menu>)

    suspend fun getMenus(): Flow<List<Menu>>
}
