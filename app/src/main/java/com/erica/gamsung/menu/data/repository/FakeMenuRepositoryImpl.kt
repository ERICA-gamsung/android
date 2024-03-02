package com.erica.gamsung.menu.data.repository

import com.erica.gamsung.menu.domain.Menu
import com.erica.gamsung.menu.domain.MenuRepository

class FakeMenuRepositoryImpl : MenuRepository {
    private var db = listOf<Menu>()

    override suspend fun updateMenus(menus: List<Menu>) {
        db = menus
    }
}
