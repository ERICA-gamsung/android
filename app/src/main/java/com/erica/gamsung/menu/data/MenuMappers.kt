package com.erica.gamsung.menu.data

import com.erica.gamsung.menu.data.local.MenuEntity
import com.erica.gamsung.menu.data.remote.GetMenusResponse
import com.erica.gamsung.menu.data.remote.UpdateMenusResponse

fun UpdateMenusResponse.toMenuEntity(): MenuEntity =
    MenuEntity(
        id = id,
        name = name,
        price = price,
    )

fun GetMenusResponse.toMenuEntity(): MenuEntity =
    MenuEntity(
        id = id,
        name = name,
        price = price,
    )
