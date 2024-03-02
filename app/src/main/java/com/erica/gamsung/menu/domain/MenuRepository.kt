package com.erica.gamsung.menu.domain

interface MenuRepository {
    suspend fun updateMenus(menus: List<Menu>)
}
