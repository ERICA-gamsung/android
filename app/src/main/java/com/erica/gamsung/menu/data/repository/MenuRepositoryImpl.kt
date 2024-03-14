package com.erica.gamsung.menu.data.repository

import com.erica.gamsung.menu.data.local.MenuDao
import com.erica.gamsung.menu.data.remote.MenuApi
import com.erica.gamsung.menu.data.remote.UpdateMenusRequest
import com.erica.gamsung.menu.data.toMenuEntity
import com.erica.gamsung.menu.domain.Menu
import com.erica.gamsung.menu.domain.MenuRepository

class MenuRepositoryImpl(
    private val menuDao: MenuDao,
    private val menuApi: MenuApi,
) : MenuRepository {
    override suspend fun updateMenus(menus: List<Menu>) {
        // 1. 서버에 메뉴 수정 요청
        val updatedMenus =
            menuApi.updateMenus(
                request =
                    menus.map { menu ->
                        UpdateMenusRequest(name = menu.name, price = menu.price)
                    },
            )

        // 2. 로컬 DB에 메뉴 저장
        menuDao.updateAll(
            updatedMenus.map { updateMenusResponse ->
                updateMenusResponse.toMenuEntity()
            },
        )
    }
}
