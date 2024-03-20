package com.erica.gamsung.menu.data.repository

import com.erica.gamsung.menu.data.local.MenuDao
import com.erica.gamsung.menu.data.remote.MenuApi
import com.erica.gamsung.menu.data.remote.UpdateMenusRequest
import com.erica.gamsung.menu.data.toMenuEntity
import com.erica.gamsung.menu.domain.Menu
import com.erica.gamsung.menu.domain.MenuRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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

    override suspend fun getMenus(): Flow<List<Menu>> =
        flow {
            // 1. 로컬 DB에 있는 데이터 반환
            val localData = menuDao.getAll().map { it.toDomainModel() }
            emit(localData)

            // 2. 서버에서 받은 데이터 동기화 및 반환
            runCatching {
                menuApi.getMenus()
            }.onSuccess { remoteData ->
                menuDao.updateAll(remoteData.map { it.toMenuEntity() })
                emit(remoteData.map { it.toDomainModel() })
            }
        }
}
