package com.erica.gamsung.menu.data.repository

import com.erica.gamsung.menu.domain.Menu
import com.erica.gamsung.menu.domain.MenuRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeMenuRepositoryImpl : MenuRepository {
    private var db = listOf<Menu>()

    override suspend fun updateMenus(menus: List<Menu>) {
        db = menus
    }

    override suspend fun getMenus(): Flow<List<Menu>> = flowOf(db)
}
